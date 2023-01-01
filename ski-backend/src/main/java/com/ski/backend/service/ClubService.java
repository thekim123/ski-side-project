package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.club.*;
import com.ski.backend.domain.common.Status;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.web.dto.ClubRequestDto;
import com.ski.backend.web.dto.ClubResponseDto;
import com.ski.backend.web.dto.ClubUserRespDto;
import com.ski.backend.repository.*;
import com.ski.backend.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubUserRepository clubUserRepository;

    private final UserRepository userRepository;
    private final ResortRepository resortRepository;

    private final ChatRoomRepository chatRoomRepository;


    // 동호회 첫 화면 목록조회
    @Transactional(readOnly = true)
    public Page<ClubResponseDto> clubList(Pageable pageable) {
        Page<Club> clubPage = clubRepository.findAll(pageable);
        Page<ClubResponseDto> dto = clubPage.map(e -> new ClubResponseDto(e.getId(), e.getMemberCnt(), e.getClubNm(), e.getResort().getId(), e.getOpenYn()));
        return dto;

    }

    // 동호회별 유저목록 조회
    @Transactional(readOnly = true)
    public Page<ClubUserRespDto> getUserListByClub(Pageable pageable, Long clubId) {
        return clubUserRepository.findByClub_Id(pageable, clubId).map(ClubUserRespDto::new);
    }

    // 유저별 동호회 목록
    @Transactional(readOnly = true)
    public Page<ClubResponseDto> getClubListByUser(Pageable pageable, Long userId) {
        Page<ClubUser> clubData = clubUserRepository.findByUser_Id(pageable, userId);
        Page<ClubResponseDto> result = clubData.map(e -> new ClubResponseDto(e.getId(), e.getClub().getClubNm(), e.getClub().getMemo(), e.getClub().getOpenYn(), e.getClub().getMemberCnt()));

        return result;
    }


    // 동호회 생성
    @Transactional
    public void create(ClubRequestDto dto, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new CustomApiException("사용자를 찾지 못했습니다."));
        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(() -> new CustomApiException("리조트를 찾지 못했습니다."));
        Club club = dto.toEntity(user, resort);

        clubRepository.save(club);
        ClubUser clubUser = new ClubUser(club, user, Status.ADMIT, "관리자");
        clubUserRepository.save(clubUser);

        // 관리자 채팅방 추가
        ChatRoom adminChatRoom = ChatRoom.builder()
                .user(findUser)
                .roomName(club.getClubNm() + "ADMIN")
                .build();
        chatRoomRepository.save(adminChatRoom);
    }

    // 동호회 삭제
    @Transactional
    public void delete(long clubId, Authentication auth) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomApiException("해당 동호회를 찾을 수 없습니다."));
        validateClubId(clubId, auth);
        clubRepository.delete(club);
    }

    // 동호회 수정
    @Transactional
    public void update(long clubId, ClubRequestDto dto, Authentication auth) {
        Club clubs = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("동호회 수정 실패"));

        validateClubId(clubId, auth);
        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(() -> new IllegalArgumentException("동호회 리조트 찾기  실패"));
        clubs.update(dto, resort);

    }


    // 동호회 탈퇴
    public void deleteMember(long userId, long clubId) {
        ClubUser clubUser = clubUserRepository.findByUserIdAndClubId(userId, clubId, Status.ADMIT).orElseThrow(() -> new CustomApiException("동호회 탈퇴 실패했습니다."));
        if (!clubUser.getRole().equals("관리자")) {
            clubUserRepository.delete(clubUser);
            clubUser.getClub().setMemberCnt(-1);
        } else {
            throw new CustomApiException("관리자는 방을 탈퇴할 수 없습니다.");
        }
    }

    // 동호회 가입 신청
    @Transactional
    public void enrollClub(long userId, long clubId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("사용자를 찾을 수 없습니다"));
        List<ClubUser> clubUser = clubUserRepository.findByClub_IdAndUser_Id(clubId, userId);
        if (clubUser.size() > 0) {
            throw new CustomApiException("이미 가입신청한 사용자입니다");
        }
        Club club = clubRepository.findById(clubId).get();

        ClubUser cu = new ClubUser(club, user);
        if (cu.getStatus().equals(Status.ADMIT)) {
            club.addMember();
        }
        clubUserRepository.save(cu);
    }

    // 동호회 가입 대기자 리스트 확인
    @Transactional(readOnly = true)
    public List<ClubUserRespDto> getClubWaitingList(long clubId) {

        List<ClubUser> result = clubUserRepository.findByClubIdAAndStatus(clubId, Status.WAITING);
        if (result.size() < 0) {
            throw new CustomApiException("대기자가 없습니다.");
        }
        List<ClubUserRespDto> waitingList = result.stream().map(e -> new ClubUserRespDto(e.getUser().getUsername(), e.getStatus(), e.getRole())).collect(Collectors.toList());
        return waitingList;
    }

    // 가입 대기자 승인 / 거절
    @Transactional
    public CmRespDto updateJoiningStatus(long clubId, long userId, Authentication auth, boolean admitYn) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomApiException("동호회를 찾지 못했습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("사용자를 찾을 수 없습니다"));

        List<ClubUser> cu = clubUserRepository.findByClubIdAAndStatus(clubId, Status.WAITING);
        if (cu.size() == 0) {
            throw new CustomApiException("대기자가 없습니다");
        }
        validateClubId(clubId, auth);
        for (ClubUser clubUser : cu) {
            if (clubUser.getUser().getId() == userId) {
                if (admitYn) {
                    clubUser.update();
                    club.addMember();

                    insertChatrooms(club, cu, clubUser);


                    return new CmRespDto<>(1, "가입 승인 완료", null);
                } else {
                    clubUser.decline();
                    return new CmRespDto<>(1, "가입 거절 완료", null);
                }
            }
        }
        throw new IllegalArgumentException();
    }

    // 나의 신청 내역

    public List<ClubUserRespDto> requestList(Authentication auth) {
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();

        List<ClubUser> result = clubUserRepository.findByIdUserId(pd.getUser().getId());
        if (result.size() == 0) {
            throw new CustomApiException("신청 내역이 없습니다.");
        }
        List<ClubUserRespDto> requestLists = result.stream().map(e -> new ClubUserRespDto(e.getUser().getUsername(), e.getStatus(), e.getRole())).collect(Collectors.toList());

        return requestLists;
    }

    public void validateClubId(long clubId, Authentication auth) {
        // 클럽아이디와 clubUserId에 등록된 클럽아이디와 role이관리자인게 일치해야함
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();
        String role = "관리자";

        ClubUser cu = clubUserRepository.findByClubId(clubId, pd.getUser().getId(), role).orElseThrow(() -> new CustomApiException("관리자만 동호회를 수정 / 삭제할 수 있습니다."));

        if (cu.getClub().getId() != clubId) throw new CustomApiException("관리자만 동호회를 수정 / 삭제할 수 있습니다");

    }

    private void insertChatrooms(Club club, List<ClubUser> cu, ClubUser clubUser) {
        String roomName = club.getClubNm() + clubUser.getId();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(roomName)
                .user(clubUser.getUser())
                .build();
        chatRoomRepository.save(chatRoom);


        cu.forEach(c -> {
            if ("관리자".equals(c.getRole())) {
                ChatRoom chatRoomOfAdmin = ChatRoom.builder()
                        .user(c.getUser())
                        .roomName(roomName)
                        .build();
                chatRoomRepository.save(chatRoomOfAdmin);

            }
        });
    }
    // 동호회 상세페이지
    public Optional<ClubResponseDto> clubDetail(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomApiException("글 상세보기 실패: 해당 게시글을 찾을수 없습니다."));
        return clubRepository.findById(clubId).map(e -> new ClubResponseDto(club));
    }



}
