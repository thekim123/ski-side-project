package com.project.ski.web;


import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.user.Users;
import com.project.ski.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/board/write")
    public String getBoardList(@RequestBody Board board, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Users user = principalDetails.getUser();
        System.out.println(user.getUsername());
        //boardService.writeBoard(board, user);
        return "글쓰기 완료";
    }

}
