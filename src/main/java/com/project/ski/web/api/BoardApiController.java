package com.project.ski.web.api;


import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.board.Comment;
import com.project.ski.domain.user.User;
import com.project.ski.service.BoardService;
import com.project.ski.service.BookmarkService;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApiController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;

    @GetMapping("/")
    public CmRespDto<?> boardList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        Page<Board> pages = boardService.getBoardList(pageable, principalId);
        return new CmRespDto<>(1, "글 조회 완료", pages);
    }

    @GetMapping("/popular")
    public CmRespDto<?> popular() {
        Page<Board> pages = boardService.getPopular();
        return new CmRespDto<>(1, "인기페이지 조회 완료", pages)
    }

    @PostMapping("write")
    public CmRespDto<?> write(@RequestBody Board board, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        boardService.write(board, user);
        return new CmRespDto<>(1, "글쓰기 완료", null);
    }

    @DeleteMapping("/delete/{boardId}")
    public CmRespDto<?> delete(@PathVariable long boardId) {
        boardService.delete(boardId);
        return new CmRespDto<>(HttpStatus.OK.value(), "글 삭제 완료", null);
    }

    @PutMapping("/update/{boardId}")
    public CmRespDto<?> update(@PathVariable long boardId, BoardRequestDto dto) {
        boardService.update(boardId, dto);
        return new CmRespDto<>(HttpStatus.OK.value(), "글 수정 완료", null);
    }

    @PostMapping("{boardId}/likes")
    public void like(@PathVariable long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Long principalId = principalDetails.getUser().getId();
        likesService.like(boardId, principalId);
    }

    @DeleteMapping("{boardId}/unlikes")
    public void unlike(@PathVariable long boardId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Long principalId = principalDetails.getUser().getId();
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
