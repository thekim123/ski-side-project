package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.user.entity.User;
import com.ski.backend.service.TayoService;
import com.ski.backend.web.dto.CmRespDto;
import com.ski.backend.web.dto.TayoRequestDto;
import com.ski.backend.web.dto.TayoRespDto;
import com.ski.backend.web.dto.TayoUserRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tayo")
public class TayoApiController {

    private final TayoService tayoService;

    // 같이 타요 전체 조회
    @GetMapping
    public CmRespDto<Page<TayoUserRespDto>> tayoList(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable, Authentication auth) {
        PrincipalDetails principalId = (PrincipalDetails) auth.getPrincipal();
        long id = principalId.getUser().getId();
        Page<TayoUserRespDto> tayoListResult = tayoService.tayoList(pageable, id);
        return new CmRespDto<>(1, "같이타요 리스트 조회 완료", tayoListResult);
    }

    // 스키장별 같이타요 조회
    @GetMapping("/{resortId}")
    public CmRespDto<List<TayoRespDto>> getTayoByResortId(@PathVariable long resortId) {
        List<TayoRespDto> tayoResortList = tayoService.getTayoByResortId(resortId);
        return new CmRespDto<>(1, "같이타요 리조트별 리스트 조회 완료", tayoResortList);

    }

    // 타요 상세 조회
    @GetMapping("/detail/{tayoId}")
    public CmRespDto<TayoRespDto> getTayoDetail(@PathVariable long tayoId) {
        TayoRespDto tayoDetail = tayoService.getTayoDetail(tayoId);
        return new CmRespDto<>(1, "같이 타요 상세조회 완료", tayoDetail);
    }

    // 타요 생성
    @PostMapping
    public CmRespDto<TayoRequestDto> create(@Valid @RequestBody TayoRequestDto dto, BindingResult bindingResult, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        tayoService.create(dto, user);
        return new CmRespDto<>(1, "같이 타요 생성 완료", dto);
    }

    // 타요 삭제
    @DeleteMapping("/delete/{tayoId}")
    public CmRespDto<Void> delete(@PathVariable long tayoId, Authentication auth) {
        tayoService.delete(tayoId, auth);

        return new CmRespDto<>(1, "같이 타요 삭제 완료", null);
    }

    // 타요 수정
    @PutMapping("/update/{tayoId}")
    public CmRespDto<TayoRequestDto> update(@PathVariable long tayoId, @Valid @RequestBody TayoRequestDto dto, BindingResult bindingResult, Authentication auth) {
        tayoService.update(tayoId, dto, auth);

        return new CmRespDto<>(1, "같이 타요 수정 완료", dto);
    }

    // 타요 가입 신청
    @PostMapping("/{userId}/enroll/{tayoId}")
    public CmRespDto<TayoRespDto> enrollTayo(@PathVariable long tayoId, @PathVariable long userId) {
        tayoService.enrollTayo(userId, tayoId);
        return new CmRespDto<>(1, "같이 타요 참여 신청 완료", null);
    }

    // 타요 승인/ 거절
    @PutMapping("/join/{tayoId}/{userId}/{joinYn}")
    public CmRespDto joinAdmit(@PathVariable long tayoId, @PathVariable long userId, Authentication auth, @PathVariable boolean joinYn) {
        return tayoService.updateJoinYnTayo(tayoId, userId, auth, joinYn);
    }

    // 타요별 신청내역
    @GetMapping("/{tayoId}/list")
    public CmRespDto<List<TayoUserRespDto>> getTayoList(@PathVariable long tayoId) {
        List<TayoUserRespDto> listByTayo = tayoService.getListByTayo(tayoId);
        return new CmRespDto<>(1, "타요별 신청 내역 조회", listByTayo);
    }

    // 나의 타요 신청내역
    @GetMapping("/{userId}/requestList")
    public CmRespDto<List<TayoUserRespDto>> getRequestList(Authentication auth) {
        List<TayoUserRespDto> requestList = tayoService.requestList(auth);
        return new CmRespDto<>(1, "같이 타요 나의 신청내역 조회", requestList);
    }
}


