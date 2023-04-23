package com.ski.backend.service;


import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.Tayo.Role;
import com.ski.backend.domain.Tayo.Tayo;
import com.ski.backend.domain.Tayo.TayoUser;
import com.ski.backend.domain.common.Status;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.user.entity.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.ResortRepository;
import com.ski.backend.repository.TayoRepository;
import com.ski.backend.repository.TayoUserRepository;
import com.ski.backend.user.repository.UserRepository;
import com.ski.backend.web.dto.CmRespDto;
import com.ski.backend.web.dto.TayoRequestDto;
import com.ski.backend.web.dto.TayoRespDto;
import com.ski.backend.web.dto.TayoUserRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TayoService {

    private final TayoRepository tayoRepository;

    private final ResortRepository resortRepository;

    private final TayoUserRepository tayoUserRepository;

    private final UserRepository userRepository;

    // 같이 타요 전체 조회
    @Transactional(readOnly = true)
    public Page<TayoUserRespDto> tayoList(Pageable pageable, long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new CustomApiException("사용자를 찾지 못했습니다."));
        Page<TayoUser> tayoUsers = tayoUserRepository.findAll(pageable);
        Page<TayoUserRespDto> tayoUserList = tayoUsers.map(e -> new TayoUserRespDto(e.getTayo(), e.getUser().getId(), e.getUser().getNickname()));
        return tayoUserList;
    }

    // 스키장별 목록조회
    @Transactional(readOnly = true)
    public List<TayoRespDto> getTayoByResortId(long resortId) {
        resortRepository.findById(resortId).orElseThrow(() -> new CustomApiException("등록되지 않은 리조트 입니다."));
        List<TayoRespDto> tayoResortList = tayoRepository.findByResortId(resortId).stream().map(e -> new TayoRespDto(e.getId(),e.getResort().getId(), e.getRideDevice(), e.getTitle(), e.getTayoMemCnt(),e.getCurTayoMemCnt(),e.getAge(), e.getTayoDt(), e.getTayoStrTime(), e.getTayoEndTime())).collect(Collectors.toList());
        return tayoResortList;
    }

    // 타요 상세 조회
    @Transactional(readOnly = true)
    public TayoRespDto getTayoDetail(long tayoId) {
        Tayo tayo = tayoRepository.findById(tayoId).orElseThrow(() -> new CustomApiException("해당 글을 찾지 못했습니다."));
        Long userId = tayoUserRepository.findByTayoId(tayoId);
        User findUser = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("사용자를 찾지 못했습니다."));
        Tayo tayos = tayoRepository.findByTayoIds(tayoId);
        return new TayoRespDto(tayos,findUser);

    }

    // 같이 타요 생성
    @Transactional
    public void create(TayoRequestDto dto, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new CustomApiException("사용자를 찾지 못했습니다."));
        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(()->{
            return new IllegalArgumentException("리조트명 찾기 실패");
        });
        Tayo tayos = dto.toEntity(user,resort);
        tayoRepository.save(tayos);
        TayoUser tayoUser = new TayoUser(tayos,user, Status.ADMIT, Role.ADMIN);
        tayoUserRepository.save(tayoUser);
    }
    // 타요 삭제
    @Transactional
    public void delete(long tayoId,Authentication auth) {
        Tayo tayos = tayoRepository.findById(tayoId).orElseThrow(()-> new IllegalArgumentException("해당 글을 찾지 못했습니다."));
        validateTayo(tayoId,auth);
        tayoRepository.delete(tayos);
    }

    // 타요 수정
    @Transactional
    public void update(long tayoId, TayoRequestDto dto,Authentication auth) {
        Tayo tayos = tayoRepository.findById(tayoId).orElseThrow(()-> new IllegalArgumentException("해당 글을 찾지 못했습니다."));
        validateTayo(tayoId,auth);
        tayos.update(dto);
    }

    // 타요 신청
    @Transactional
    public void enrollTayo(long userId, long tayoId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("사용자를 찾지 못했습니다."));
        List<TayoUser> tayoUserList = tayoUserRepository.findByTayo_IdAndUser_Id(tayoId, userId);

        if (tayoUserList.size() > 0) {
            throw new CustomApiException("이미 가입 신청한 유저입니다.");
        }
        Tayo tayo = tayoRepository.findById(tayoId).get();

        TayoUser tayoUser = new TayoUser(tayo, user);
        tayoUserRepository.save(tayoUser);
    }

    // 타요 승인 or 거절
    @Transactional
    public CmRespDto updateJoinYnTayo( long tayoId,long userId, Authentication auth,boolean joinYn) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomApiException("사용자를 찾지 못했습니다."));
        Tayo tayo = tayoRepository.findById(tayoId).orElseThrow(() -> new CustomApiException("해당 게시글을 찾지 못했습니다"));

        List<TayoUser> tayoUsers = tayoUserRepository.findByTayoIdAndStatus(tayoId, Status.WAITING);

        if (tayoUsers.size() == 0) {
            throw new CustomApiException("대기자가 없습니다");
        }
        validateTayo(tayoId,auth);
        for (TayoUser tu : tayoUsers) {
            if (tu.getUser().getId() == userId && tu.getTayo().getTayoMemCnt() > tu.getTayo().getCurTayoMemCnt()) {
                if (joinYn) {
                    tu.admit();
                    tayo.addMember();
                    return new CmRespDto<>(1, "타요 승인 완료", null);
                }else{
                    tu.decline();
                    return new CmRespDto<>(1, "타요 거절 완료", null);
                }
            }
            throw new CustomApiException("정원 초과되었습니다.");
        }
        throw new IllegalArgumentException();
    }

    // 타요별 신청내역
    public List<TayoUserRespDto> getListByTayo(long tayoId) {
        Tayo tayo = tayoRepository.findById(tayoId).orElseThrow(() -> new CustomApiException("해당 게시글은 없습니다"));
        List<TayoUser> result = tayoUserRepository.findByTayoIds(tayoId);
        if (result.size() == 0) {
            throw new CustomApiException("신청 내역이 없습니다.");
        }
        List<TayoUserRespDto> tayoUserRespDtoList = result.stream().map(e -> new TayoUserRespDto(e.getTayo(), e.getStatus(), e.getUser().getUsername())).collect(Collectors.toList());
        return tayoUserRespDtoList;
    }

    // 타요 나의 신청내역
    public List<TayoUserRespDto> requestList(Authentication auth) {
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();
        List<TayoUser> tayoUserList = tayoUserRepository.findByUserId(pd.getUser().getId());
        if (tayoUserList.size() == 0) {
            throw new CustomApiException("신청 내역이 없습니다.");
        }
        List<TayoUserRespDto> result = tayoUserList.stream()
                .map(e -> new TayoUserRespDto(e.getTayo(), e.getStatus(), e.getUser().getUsername())).collect(Collectors.toList());

        return result;
    }
    // 같이 타요 해당 작성자가 아닌경우 수정 삭제 안되게
    public void validateTayo(long tayoId, Authentication auth) {
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();


        TayoUser tu = tayoUserRepository.findByTayoId(tayoId, pd.getUser().getId(), Role.ADMIN).orElseThrow(() -> new CustomApiException("관리자만 접근 할 수 있습니다."));

        if (tayoId != tu.getTayo().getId()) {
            throw new CustomApiException("작성자만 접근 할 수 있습니다.");
        }
    }
}
