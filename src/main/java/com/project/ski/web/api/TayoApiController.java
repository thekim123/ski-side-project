package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.Tayo.Tayo;
import com.project.ski.domain.user.User;
import com.project.ski.service.TayoService;
import com.project.ski.web.dto.CmRespDto;
import com.project.ski.web.dto.TayoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tayo")
public class TayoApiController {

    private final TayoService tayoService;

    @GetMapping
    public CmRespDto<Page<Tayo>> tayoList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication auth) {
        PrincipalDetails principalId = (PrincipalDetails) auth.getPrincipal();
        long id = principalId.getUser().getId();

        Page<Tayo> tayoPage = tayoService.tayoList(pageable, id);
        return new CmRespDto<>(1, "같이타요 리스트 조회 완료", tayoPage);
    }

    //, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    @GetMapping("/{resortName}")
    public CmRespDto<?> getTayoByResortName(@PathVariable String resortName) {
        List<Tayo> tayos = tayoService.getTayoByResortName(resortName);

        return new CmRespDto<>(1, "같이타요 리조트별 리스트 조회 완료", tayos);

    }

    // 타요 생성
    @PostMapping
    public CmRespDto<?> create(@RequestBody TayoRequestDto dto, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        tayoService.create(dto, user);
        return new CmRespDto<>(1, "같이 타요 생성 완료", null);
    }

    // 타요 삭제
    @DeleteMapping("/delete/{tayoId}")
    public CmRespDto<?> delete(@PathVariable long tayoId) {
        tayoService.delete(tayoId);

        return new CmRespDto<>(1, "같이 타요 삭제 완료", null);
    }

    // 타요 수정
    @PutMapping("/update/{tayoId}")
    public CmRespDto<?> update(@PathVariable long tayoId, @RequestBody TayoRequestDto dto) {
        tayoService.update(tayoId,dto);

        return new CmRespDto<>(1, "같이 타요 수정 완료", dto);
    }
}


