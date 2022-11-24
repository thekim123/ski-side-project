package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.carpool.Carpool;
import com.project.ski.domain.user.User;
import com.project.ski.service.CarpoolService;
import com.project.ski.web.dto.CarpoolRequestDto;
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
@RequestMapping("/api/carpool")
public class CarpoolApiController {

    private final CarpoolService carpoolService;

    @PostMapping
    public CmRespDto<?> insert(@RequestBody CarpoolRequestDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        carpoolService.write(dto, user);
        return new CmRespDto<>(1, "카풀 게시글 등록 성공", null);
    }

    @PutMapping("/update/{carpoolId}")
    public CmRespDto<?> update(@RequestBody CarpoolRequestDto dto, @PathVariable long carpoolId) {
        carpoolService.update(dto, carpoolId);
        return new CmRespDto<>(1, "카풀 게시글 수정 성공", null);
    }

    @DeleteMapping("/delete/{carpoolId}")
    public CmRespDto<?> delete(@PathVariable long carpoolId) {
        carpoolService.delete(carpoolId);
        return new CmRespDto<>(1, "카풀 게시글 삭제 성공", null);
    }

    @GetMapping
    public CmRespDto<?> getAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Carpool> pages =  carpoolService.getAll(pageable);
        return new CmRespDto<>(1, "카풀 게시글 모두 불러오기 성공", pages);
    }
}
