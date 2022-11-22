package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.user.User;
import com.project.ski.service.ClubBoardService;
import com.project.ski.web.dto.ClubBoardDto;
import com.project.ski.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubBoard")
public class ClubBoardApiController {

    private final ClubBoardService clubBoardService;


    @PostMapping
    public CmRespDto<ClubBoardDto> create(@RequestBody ClubBoardDto dto, Authentication auth, @PathVariable long clubId) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        ClubBoardDto clubBoard = clubBoardService.createClubBoard(dto, user, clubId);
        return new CmRespDto<>(1, "클럽게시판 생성 완료", clubBoard);
    }

    @GetMapping("/{clubBoardId}")
    public CmRespDto<ClubBoardDto> getClubBoard(@PathVariable long clubBoardId) {
        clubBoardService.getClubBoard(clubBoardId);
        return new CmRespDto<>(1, "클럽게시판 조회 완료",null);
    }
}
