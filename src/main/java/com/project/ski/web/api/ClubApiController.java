package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.club.Club;
import com.project.ski.domain.user.User;
import com.project.ski.service.ClubService;
import com.project.ski.web.dto.ClubRequestDto;
import com.project.ski.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/club")
public class ClubApiController {

    private final ClubService clubService;

    // 목록 조회
    @GetMapping
    public CmRespDto<Page<Club>> clubList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication auth) {

        PrincipalDetails principalId = (PrincipalDetails) auth.getPrincipal();
        long id = principalId.getUser().getId();
        Page<Club> clubPage = clubService.clubList(pageable, id);
        return new CmRespDto<>(1, "동호회 리스트 조회 완료", clubPage);
    }

    // 동호회 생성
    @PostMapping("createClub")
    public CmRespDto<Club> createClub(@RequestBody ClubRequestDto dto, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        clubService.createClub(dto, user);
        return new CmRespDto<>(1, "동호회 생성 완료", null);
    }

    // 동호회 삭제
    @DeleteMapping("/deleteClub/{clubId}")
    public CmRespDto<Club> deleteClub(@PathVariable long clubId) {
        clubService.deleteClub(clubId);
        return new CmRespDto<>(1, "동호회 삭제 완료", null);
    }


}
