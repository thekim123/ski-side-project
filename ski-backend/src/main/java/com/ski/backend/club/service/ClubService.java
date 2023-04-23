package com.ski.backend.club.service;

import com.ski.backend.club.entity.Club;
import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.common.Status;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.club.repository.ClubRepository;
import com.ski.backend.club.repository.ClubUserRepository;
import com.ski.backend.web.dto.club.ClubRequestDto;
import com.ski.backend.web.dto.club.ClubResponseDto;
import com.ski.backend.web.dto.club.ClubUserRespDto;
import com.ski.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ski.backend.club.entity.Role.*;


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
        return clubPage.map(e -> ClubResponseDto.builder()
                .id(e.getId())
                .memberCnt(e.getMemberCnt())
                .clubNm(e.getClubNm())
                .resortId(e.getResort().getId())
                .openYn(e.getOpenYn())
                .build());
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
        return clubData.map(e -> ClubResponseDto.builder()
                .id(e.getId())
                .clubNm(e.getClub().getClubNm())
                .openYn(e.getClub().getOpenYn())
                .memberCnt(e.getClub().getMemberCnt())
                .memo(e.getClub().getMemo())
                .build());
    }


    // 동호회 생성
    @Transactional
    public void create(ClubRequestDto dto, User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("사용자를 찾지 못했습니다."));
        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(() -> new EntityNotFoundException("리조트를 찾지 못했습니다."));
        Club club = dto.toEntity(user, resort);

        clubRepository.save(club);
        ClubUser clubUser = new ClubUser(club, user, Status.ADMIT, ADMIN);
        clubUserRepository.save(clubUser);

        // 관리자 채팅방 추가
        ChatRoom adminChatRoom = ChatRoom.builder()
                .user(findUser)
                .roomName(club.getClubNm())
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
        ClubUser clubUser = clubUserRepository.findByUserIdAndClubIdAndStatus(userId, clubId, Status.ADMIT).orElseThrow(() -> new EntityNotFoundException("동호회 탈퇴 실패했습니다."));

        /*
            채팅방 나가기를 위한 채팅방 이름 문자열 만들기
         */
        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
            throw new CustomApiException("클럽을 찾을 수가 없습니다.");
        });
        String chatRoomName = club.getClubNm();
        chatRoomName += clubUser.getId();
        System.out.println(chatRoomName);
        // 여기까지

        if (!clubUser.getRole().equals(ADMIN)) {
            clubUserRepository.delete(clubUser);
            clubUser.getClub().setMemberCnt(-1);
        } else {
            throw new CustomApiException("관리자는 방을 탈퇴할 수 없습니다.");
        }

    }

    // 동호회 가입 신청
    @Transactional
    public void enrollClub(Authentication auth, long clubId) {
        User loginUser = ((PrincipalDetails) auth.getPrincipal()).getUser();
        List<ClubUser> clubUser = clubUserRepository.findByClub_IdAndUser_Id(clubId, loginUser.getId());
        if (clubUser.size() > 0) {
            throw new CustomApiException("이미 가입신청한 사용자입니다");
        }
        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
            throw new EntityNotFoundException("해당 클럽이 존재하지 않습니다.");
        });

        ClubUser cu = new ClubUser(club, loginUser);
        if (cu.getStatus().equals(Status.ADMIT)) {
            club.addMember();
        }

        if (club.getOpenYn().equals("Y")) {
            insertChatrooms(club, cu);
        }
        clubUserRepository.save(cu);
    }

    // 동호회 가입 대기자 리스트 확인
    @Transactional(readOnly = true)
    public List<ClubUserRespDto> getClubWaitingList(long clubId) {
        List<ClubUser> result = clubUserRepository.findByClubIdAndStatus(clubId, Status.WAITING);
        return result.stream().map(e -> new ClubUserRespDto(e.getUser().getUsername(), e.getStatus(), e.getRole())).collect(Collectors.toList());
    }

    // 가입 대기자 승인 / 거절
    @Transactional
    public String updateJoiningStatus(long clubId, long userId, Authentication auth, boolean admitYn) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new EntityNotFoundException("동호회를 찾지 못했습니다."));

        List<ClubUser> cu = clubUserRepository.findByClubIdAndStatus(clubId, Status.WAITING);
        if (cu.size() == 0) {
            throw new CustomApiException("대기자가 없습니다");
        }
        validateClubId(clubId, auth);

        String result = "아무일도 일어나지 않았습니다.";
        for (ClubUser clubUser : cu) {
            if (clubUser.getUser().getId() == userId) {
                if (admitYn) {
                    clubUser.update();
                    club.addMember();

                    // 클럽 가입 처리시 관리자와 신규 가입자 본인 채팅방 다중 insert
                    List<ChatRoom> chatroomList = insertChatrooms(club, clubUser);
                    chatRoomRepository.saveAll(chatroomList);
                    return "가입 승인 완료";
                } else {
                    clubUser.decline();
                    return "가입 거절 완료";
                }
            }
        }
        return result;
    }

    /**
     * 클럽 권한 수정 서비스 로직
     *
     * @param clubUserId 클럽 유저 아이디
     * @param auth       로그인 정보
     * @param role       역할 이름
     */
    @Transactional
    public void updateRole(long clubUserId, Authentication auth, String role) {
        ClubUser clubUser = clubUserRepository.findById(clubUserId).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        User loginUser = ((PrincipalDetails) auth.getPrincipal()).getUser();
        User clubAdmin = clubUser.getClub().getClubAdmin();

        if (!Objects.equals(loginUser.getUsername(), clubAdmin.getUsername())) {
            throw new AccessDeniedException("클럽장만이 권한을 수정할 수 있습니다.");
        }
        clubUser.updateRole(role);
    }


    // 나의 신청 내역
    public List<ClubUserRespDto> requestList(Authentication auth) {
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();

        List<ClubUser> result = clubUserRepository.findByUserId(pd.getUser().getId());
        if (result.size() == 0) {
            throw new CustomApiException("신청 내역이 없습니다.");
        }

        return result.stream().map(e -> new ClubUserRespDto(e.getUser().getUsername(), e.getStatus(), e.getRole())).collect(Collectors.toList());
    }

    public void validateClubId(long clubId, Authentication auth) {
        // 클럽아이디와 clubUserId에 등록된 클럽아이디와 role 이 관리자인게 일치해야함
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();
        ClubUser cu = clubUserRepository.findByIdAndUserAndRole(clubId, pd.getUser(), ADMIN).orElseThrow(() -> new AccessDeniedException("관리자만 동호회를 수정 / 삭제할 수 있습니다."));
        if (cu.getClub().getId() != clubId) throw new AccessDeniedException("관리자만 동호회를 수정 / 삭제할 수 있습니다");
    }

    // 동호회 상세페이지
    public Optional<ClubResponseDto> clubDetail(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomApiException("글 상세보기 실패: 해당 게시글을 찾을수 없습니다."));
        return clubRepository.findById(clubId).map(e -> new ClubResponseDto(club));
    }

    // 클럽 가입 처리시 관리자와 신규 가입자 본인 채팅방 다중 insert 하는 매서드
    private List<ChatRoom> insertChatrooms(Club club, ClubUser clubUser) {
        String roomName = club.getClubNm() + "-" + clubUser.getUser().getNickname();
        List<ChatRoom> chatRooms = new ArrayList<>();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(roomName)
                .user(clubUser.getUser())
                .club(club)
                .build();
        chatRooms.add(chatRoom);

        club.getClubUsers().forEach(c -> {
            if (c.getRole().equals(ADMIN)) {
                ChatRoom chatRoomOfAdmin = ChatRoom.builder()
                        .user(c.getUser())
                        .roomName(roomName)
                        .club(club)
                        .build();
                chatRooms.add(chatRoomOfAdmin);
            }
        });

        return chatRooms;

    }


}
