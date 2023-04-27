package com.ski.backend.board.service;

import com.ski.backend.config.AuditorProvider;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.board.entity.Board;
import com.ski.backend.resort.entity.Resort;
import com.ski.backend.resort.entity.ResortName;
import com.ski.backend.user.entity.User;
import com.ski.backend.board.repository.BoardRepository;
import com.ski.backend.resort.repository.ResortRepository;
import com.ski.backend.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Board board = Board.builder()
                .user(user)
                .resort(resort)
                .content(dto.getContent())
                .title(dto.getTitle())
                .pageCount(0)
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void delete(long boardId) {
        Board boardEntity = boardRepository.findById(boardId).orElseThrow(() ->
                new EntityNotFoundException("게시글의 등록번호를 찾을 수 없습니다."));

        AuditorProvider ad = new AuditorProvider();
        if (isBoardOwner(boardEntity.getUser().getUsername(), ad.getCurrentAuditor())) {
            throw new AccessDeniedException("글작성자만이 수정할 수 있습니다.");
        }

        boardRepository.delete(boardEntity);
    }

    @Transactional
    public void update(BoardDto.Save dto) {
        Board boardEntity = boardRepository.findById(dto.getId()).orElseThrow(() ->
                new EntityNotFoundException("게시글의 등록번호를 찾을 수 없습니다."));

        AuditorProvider ad = new AuditorProvider();
        if (isBoardOwner(boardEntity.getUser().getUsername(), ad.getCurrentAuditor())) {
            throw new AccessDeniedException("글작성자만이 수정할 수 있습니다.");
        }

        Resort resort = findResort(dto);
        boardEntity.update(dto, resort);

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

        // TODO: effective java 에서 enum 으로 상수형 자료를 만들었떤걸 본적이 있는데 그걸 활용해서 이 쿼리를 없애자.
        Resort resort = resortRepository.findByResortName(name).orElseThrow(() -> {
            throw new EntityNotFoundException("리조트를 찾을 수 없습니다.");
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
        Board boardEntity = boardRepository.findById(boardId).orElseThrow(() ->
                new EntityNotFoundException("게시글의 등록번호를 찾을 수 없습니다."));
        boardEntity.loadLikesAndDislikes(principalId);
        return boardEntity;
    }


    /**
     * @param writer        글작성자 username
     * @param loginUsername 로그인 유저의 username
     * @return 글 작성자와 로그인 유저가 동일한지 여부
     */
    public boolean isBoardOwner(String writer, Optional<String> loginUsername) {
        return loginUsername.isPresent() && Objects.equals(writer, loginUsername.get());
    }

    public Resort findResort(BoardDto.Save dto) {
        ResortName resortName = ResortName.valueOf(dto.getResortName());
        return resortRepository.findByResortName(resortName).orElseThrow(() -> {
            throw new EntityNotFoundException("리조트를 찾을 수 없습니다.");
        });
    }


}
