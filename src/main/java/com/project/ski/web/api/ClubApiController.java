package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;

import com.project.ski.domain.user.User;
import com.project.ski.service.ClubService;
import com.project.ski.web.dto.ClubRequestDto;
import com.project.ski.web.dto.ClubResponseDto;
import com.project.ski.web.dto.ClubUserRespDto;
import com.project.ski.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/club")
public class ClubApiController {

    private final ClubService clubService;



    @GetMapping
    public CmRespDto<Page<ClubResponseDto>> clubList(@PageableDefault(sort = "id", direction = DESC) Pageable pageable) {
        Page<ClubResponseDto> clubPage = clubService.clubList(pageable);
        return new CmRespDto<>(1, "동호회 리스트 조회 완료", clubPage);
    }

    // 동호회별 유저목록 조회
    @GetMapping("/{clubId}/user")
    public CmRespDto<Page<ClubUserRespDto>> getUserClubList(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, Authentication auth,@PathVariable Long clubId) {

        PrincipalDetails principalId = (PrincipalDetails) auth.getPrincipal();
        User user = principalId.getUser();

        Page<ClubUserRespDto> clubPage = clubService.getUserClubList(pageable, clubId);
        return new CmRespDto<>(1, "유저별 동호회 리스트 조회 완료",clubPage);
    }


    // 동호회 생성
    @PostMapping
    public CmRespDto<ClubRequestDto> create(@RequestBody ClubRequestDto dto, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();

        clubService.create(dto, user);

        return new CmRespDto<>(1, "동호회 생성 완료", null);
    }


    // 동호회 삭제
    @DeleteMapping("/delete/{clubId}")
    public CmRespDto<ClubResponseDto> delete(@PathVariable long clubId) {

        clubService.delete(clubId);
        return new CmRespDto<>(1, "동호회 삭제 완료", null);
    }

    // 동호회 수정
    @PutMapping("/update/{clubId}")
    public CmRespDto<ClubResponseDto> update(@PathVariable long clubId, @RequestBody ClubRequestDto dto) {
        clubService.update(clubId, dto);
        return new CmRespDto<>(1, "동호회 수정 완료", null);
    }

    // 동호회 탈퇴
    @DeleteMapping("/leave/{userId}/{clubId}")
    public CmRespDto<ClubResponseDto> deleteMember(@PathVariable long userId , @PathVariable long clubId) {
        clubService.deleteMember(userId,clubId);
        return new CmRespDto<>(1, "동호회 탈퇴 완료", null);
    }


    // 동호회 글 상세 조회 -- > 해당 동호회 게시판 목록조회
    @GetMapping("/{clubId}")
    public CmRespDto<ClubResponseDto> clubDetail(@PathVariable Long clubId) {
        clubService.clubDetail(clubId);
        return new CmRespDto<>(1, "동호회 상세보기 완료", null);
    }

    // 동호회 게시판 생성

}
