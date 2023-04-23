package com.ski.backend.board.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.resort.ResortName;
import com.ski.backend.domain.user.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.board.repository.BoardRepository;
import com.ski.backend.repository.ResortRepository;
import com.ski.backend.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ResortRepository resortRepository;

    @Transactional(readOnly = true)
    public Page<Board> getHomeBoard(Authentication authentication, Pageable pageable) {
        long principalId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        Page<Board> boards = boardRepository.homeBoard(principalId, pageable);

        boards.forEach((board) -> board.loadLikesAndDislikes(principalId));
        return boards;
    }

    @Transactional
    public void write(BoardDto.Save dto, Authentication authentication) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        Resort resort = findResort(dto);

        ModelMapper modelMapper = new ModelMapper();
        TypeMap<BoardDto.Save, Board> typeMap = modelMapper.createTypeMap(BoardDto.Save.class, Board.class);
        typeMapWithBoard(dto, typeMap);

        Board board = modelMapper.map(dto, Board.class);
        board.withUserAndResort(user, resort);

        boardRepository.save(board);
    }

    private static void typeMapWithBoard(BoardDto.Save dto, TypeMap<BoardDto.Save, Board> typeMap) {
        typeMap.setProvider(new Provider<Board>() {
            @Override
            public Board get(ProvisionRequest<Board> request) {
                BoardDto.Save source = (BoardDto.Save) request.getSource();
                return Board.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build();
            }
        });
    }

    @Transactional
    public void delete(long boardId, Authentication authentication) {
        Board boardEntity = findBoardEntityById(boardId);
        long principalId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();

        if (isMyBoard(boardEntity.getUser().getId(), principalId)) {
            throw new IllegalArgumentException("선생님의 글이 아니잖아요!!!!");
        }

        boardRepository.delete(boardEntity);
    }

    @Transactional
    public void update(BoardDto.Save dto, Authentication authentication) {
        long principalId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        Board boardEntity = findBoardEntityById(dto.getId());

        if (isMyBoard(boardEntity.getUser().getId(), principalId)) {
            throw new IllegalArgumentException("선생님의 글이 아니잖아요!!!!");
        }

        Resort resort = findResort(dto);
        boardEntity.changeData(dto, resort);

    }

    // 전체 게시글 보기
    @Transactional(readOnly = true)
    public Page<Board> getAllBoardList(Pageable pageable, long principalId) {
        Page<Board> boards = boardRepository.findAll(pageable);
        boards.forEach((board) -> board.loadLikesAndDislikes(principalId));
        return boards;
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardByResort(String resortName, Pageable pageable, Authentication authentication) {
        long principalId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        ResortName name = ResortName.valueOf(resortName);
        Resort resort = resortRepository.findByResortName(name).orElseThrow(() -> {
            throw new CustomApiException("리조트를 찾을 수 없습니다.");
        });
        long resortId = resort.getId();
        Page<Board> boards = boardRepository.findByResortId(resortId, pageable);

        boards.forEach((board) -> board.loadLikesAndDislikes(principalId));

        return boards;
    }

    // 인기게시글 보기(좋아요 순)
    @Transactional(readOnly = true)
    public List<Board> getPopular() {
        return boardRepository.getPopular();
    }

    @Transactional(readOnly = true)
    public Board getBoardDetail(long boardId, Authentication authentication) {
        long principalId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        Board boardEntity = findBoardEntityById(boardId);
        boardEntity.loadLikesAndDislikes(principalId);
        return boardEntity;
    }


    /*
     *  아래는 트랜잭션 아닌 매소드
     */

    public Board findBoardEntityById(long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("게시글의 등록번호를 찾을 수 없습니다."));
    }

    public boolean isMyBoard(long userId, long principalId) {
        return userId != principalId;
    }

    public Resort findResort(BoardDto.Save dto) {
        ResortName resortName = ResortName.valueOf(dto.getResortName());
        return resortRepository.findByResortName(resortName).orElseThrow(() -> {
            throw new CustomApiException("리조트를 찾을 수 없습니다.");
        });
    }


}
