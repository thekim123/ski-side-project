package com.ski.backend.web.api;

import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.service.CarpoolService;
import com.ski.backend.web.dto.CarpoolRequestDto;
import com.ski.backend.web.dto.CmRespDto;
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
        carpoolService.write(dto, authentication);
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

    @GetMapping("/{carpoolId}")
    public CmRespDto<?> getAll(@PathVariable long carpoolId) {
        Carpool carpool =  carpoolService.detail(carpoolId);
        return new CmRespDto<>(1, "카풀 게시글 불러오기 성공", carpool);
    }
}
