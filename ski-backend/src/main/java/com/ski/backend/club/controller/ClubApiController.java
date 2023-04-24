package com.ski.backend.club.controller;

import com.ski.backend.common.CmRespDto;
import com.ski.backend.config.auth.PrincipalDetails;

import com.ski.backend.user.entity.User;
import com.ski.backend.chat.service.ChatService;
import com.ski.backend.club.service.ClubService;
import com.ski.backend.club.dto.ClubRequestDto;
import com.ski.backend.club.dto.ClubResponseDto;
import com.ski.backend.club.dto.ClubUserRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/club")
public class ClubApiController {

    private final ClubService clubService;
    private final ChatService chatService;


    // 동호회 첫화면 조회
    @GetMapping
    public CmRespDto<?> clubList(Pageable pageable) {
        Page<ClubResponseDto> clubPage = clubService.clubList(pageable);
        return new CmRespDto<>(1, "동호회 리스트 조회 완료", clubPage);
    }

    // 동호회별 유저목록 조회
    @GetMapping("/{clubId}/user")
    public CmRespDto<?> getUserList(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, @PathVariable Long clubId) {
        Page<ClubUserRespDto> userClubList = clubService.getUserListByClub(pageable, clubId);
        return new CmRespDto<>(1, "동호회별 유저 리스트 조회 완료", userClubList);
    }

    // 유저별 동호회 목록 조회
    @GetMapping("/{userId}/club")
    public CmRespDto<?> getClubList(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, @PathVariable Long userId) {
        Page<ClubResponseDto> clubListByUser = clubService.getClubListByUser(pageable, userId);
        return new CmRespDto<>(1, "유저별 동호회 목록 조회 완료", clubListByUser);
    }

    // 동호회 생성
    @PostMapping
    public CmRespDto<?> create(@RequestBody ClubRequestDto dto, Authentication auth) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        clubService.create(dto, user);
        return new CmRespDto<>(1, "동호회 생성 완료", null);
    }

    // 동호회 삭제
    @DeleteMapping("/delete/{clubId}")
    public CmRespDto<ClubResponseDto> delete(@PathVariable long clubId, Authentication auth) {
        clubService.delete(clubId, auth);
        chatService.deleteAllChatRoomWhenDeleteClub(clubId);
        return new CmRespDto<>(1, "동호회 삭제 완료", null);
    }


    // 동호회 수정
    @PutMapping("/update/{clubId}")
    public CmRespDto<ClubResponseDto> update(@PathVariable long clubId, @RequestBody ClubRequestDto dto, Authentication auth) {
        clubService.update(clubId, dto, auth);
        return new CmRespDto<>(1, "동호회 수정 완료", null);
    }

    // 동호회 탈퇴
    @DeleteMapping("/leave/{userId}/{clubId}")
    public CmRespDto<ClubResponseDto> deleteMember(@PathVariable long userId, @PathVariable long clubId) {
        clubService.deleteMember(userId, clubId);
        chatService.deleteChatRoomWhenLeave(userId, clubId);
        return new CmRespDto<>(1, "동호회 탈퇴 완료", null);
    }

    // 동호회 가입
    @PostMapping("/enroll/{clubId}")
    public CmRespDto<ClubUserRespDto> enrollClub(Authentication auth, @PathVariable long clubId) {
        clubService.enrollClub(auth, clubId);
        return new CmRespDto<>(1, "동호회 가입 신청", null);
    }

    // 동호회 대기자리스트 조회
    @GetMapping("/{clubId}/waiting")
    public CmRespDto<List<ClubUserRespDto>> getWaitingList(@PathVariable long clubId) {
        List<ClubUserRespDto> clubWaitingList = clubService.getClubWaitingList(clubId);
        return new CmRespDto<>(1, "대기자명단 조회", clubWaitingList);

    }

    // 가입 대기자 승인 거절
    @PutMapping("/joining/{clubId}/{userId}/{admitYn}")
    public CmRespDto<?> statusAdmit(@PathVariable long clubId, @PathVariable long userId, Authentication auth, @PathVariable boolean admitYn) {
        String updateResult = clubService.updateJoiningStatus(clubId, userId, auth, admitYn);
        return new CmRespDto<>(1, updateResult, null);
    }

    // 나의 신청 내역 조회
    @GetMapping("/requestList")
    public CmRespDto<?> getRequestList(Authentication auth) {
        List<ClubUserRespDto> requestList = clubService.requestList(auth);
        return new CmRespDto<>(1, "나의 신청내역 조회", requestList);
    }


    // 동호회 상세페이지 조회
    @GetMapping("/detail/{clubId}")
    public CmRespDto<?> getClubDetail(@PathVariable long clubId) {
        Optional<ClubResponseDto> clubDetail = clubService.clubDetail(clubId);
        return new CmRespDto<>(1, "동호회 상세페이지 조회", clubDetail);
    }


    /**
     * 동호회 관리자가 회원의 권한을 변경함
     *
     * @param clubUserId 클럽에서 해당 유저의 id
     * @param auth       로그인 정보
     * @param role       변경할 역할
     * @author 김병조
     * @since 아마도 2022.11.01
     * 2023.04.23 thekim123 수정
     */
    @PutMapping("/updateRole/{clubUserId}")
    public CmRespDto<?> updateRole(@PathVariable long clubUserId, Authentication auth, @RequestParam String role) {
        clubService.updateRole(clubUserId, auth, role);
        return new CmRespDto<>(1, "권한 수정이 완료되었습니다.", null);
    }
}
