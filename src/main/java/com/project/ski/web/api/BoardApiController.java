package com.project.ski.web;


import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.user.User;
import com.project.ski.service.BoardService;
import com.project.ski.web.dto.BoardDto;
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

    @GetMapping("/")
    public CmRespDto<?> boardList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> pages = boardService.getBoardList(pageable);
        return new CmRespDto<>(1, "글 조회 완료", pages);
    }

    @PostMapping("write")
    public CmRespDto<?> write(@RequestBody Board board, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        BoardDto dto = boardService.write(board, user);
        return new CmRespDto<>(1, "글쓰기 완료", dto);
    }


    @DeleteMapping("/delete/{boardId}")
    public CmRespDto<?> delete(@PathVariable long boardId) {
        boardService.delete(boardId);
        return new CmRespDto<>(HttpStatus.OK.value(), "글 삭제 완료", null);
    }

    @PutMapping("/update/{boardId}")
    public CmRespDto<?> update(@PathVariable long boardId, BoardDto dto) {
        boardService.update(boardId, dto);
        return new CmRespDto<>(HttpStatus.OK.value(), "글 수정 완료", 1);
    }


}
