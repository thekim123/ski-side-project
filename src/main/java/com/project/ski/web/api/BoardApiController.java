package com.project.ski.web.api;


import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.user.User;
import com.project.ski.service.BoardService;
import com.project.ski.service.CommentService;
import com.project.ski.service.LikesService;
import com.project.ski.web.dto.BoardRequestDto;
import com.project.ski.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApiController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final LikesService likesService;

    @GetMapping("/home")
    public CmRespDto<?> home(Authentication authentication, @PageableDefault Pageable pageable) {
        Page<Board> pages = boardService.getHomeBoard(authentication, pageable);
        return new CmRespDto<>(1, "즐겨찾기한 게시글 조회 완료", pages);
    }

    @GetMapping("/")
    public CmRespDto<?> getAllBoard(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        Page<Board> pages = boardService.getAllBoardList(pageable, principalId);
        return new CmRespDto<>(1, "전체 게시글 조회 완료", pages);
    }

    @GetMapping("/popular")
    public CmRespDto<?> popular() {
        List<Board> pages = boardService.getPopular();
        return new CmRespDto<>(1, "인기페이지 조회 완료", pages);
    }

    @GetMapping("/{resortName}")
    public CmRespDto<?> getBoardByResort(@PathVariable String resortName, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> pages = boardService.getBoardByResort(resortName.toUpperCase(), pageable);
        return new CmRespDto<>(1, "리조트별 게시글 조회 완료", pages);
    }

    @PostMapping("write")
    public CmRespDto<?> write(@RequestBody BoardRequestDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        boardService.write(dto, user);
        return new CmRespDto<>(1, "글쓰기 완료", null);
    }

    @DeleteMapping("/delete/{boardId}")
    public CmRespDto<?> delete(@PathVariable long boardId) {
        boardService.delete(boardId);
        return new CmRespDto<>(HttpStatus.OK.value(), "글 삭제 완료", null);
    }

    @PutMapping("/update/")
    public CmRespDto<?> update(BoardRequestDto dto, Authentication authentication) {
        boardService.update(dto, authentication);
        return new CmRespDto<>(HttpStatus.OK.value(), "글 수정 완료", null);
    }

    @PostMapping("{boardId}/likes")
    public void like(@PathVariable long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        likesService.like(boardId, principalId);
    }

    @DeleteMapping("{boardId}/unlikes")
    public void unlike(@PathVariable long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        likesService.unlike(boardId, principalId);
    }

    @PostMapping("/{boardId}/comment")
    public CmRespDto<?> writeComment(
            @PathVariable long boardId,
            @RequestBody String content,
            Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User principal = principalDetails.getUser();
        commentService.write(principal, content, boardId);
        return new CmRespDto<>(1, "댓글쓰기 완료", null);
    }

}
