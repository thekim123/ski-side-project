package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.user.User;
import com.ski.backend.service.ClubBoardService;
import com.ski.backend.web.dto.club.ClubBoardDto;
import com.ski.backend.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubBoard")
public class ClubBoardApiController {

    private final ClubBoardService clubBoardService;

    // 게시판 생성
    @PostMapping
    public CmRespDto<?> create(@Valid @RequestBody ClubBoardDto dto, BindingResult bindingResult, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        clubBoardService.createClubBoard(dto, user);
        return new CmRespDto<>(1, "클럽게시판 생성 완료", null);
    }

    @GetMapping("/detail/{clubBoardId}")
    public CmRespDto<?> getClubBoard(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, @PathVariable long clubBoardId) {
        Page<ClubBoardDto> clubBoard = clubBoardService.getClubBoard(pageable, clubBoardId);
        return new CmRespDto<>(1, "클럽게시판 조회 완료", clubBoard);
    }

    @GetMapping("/{clubId}")
    public CmRespDto<?> getAllClubBoard(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, @PathVariable long clubId) {
        Page<ClubBoardDto> clubBoardDtoList = clubBoardService.getAllClubBoard(pageable, clubId);
        return new CmRespDto<>(1, "클럽게시판 전체조회 완료", clubBoardDtoList);
    }

    @PutMapping("/update/{clubBoardId}")
    public CmRespDto<?> updateClubBoard(@PathVariable long clubBoardId, @Valid @RequestBody ClubBoardDto dto, BindingResult bindingResult, Authentication auth) {
        clubBoardService.update(clubBoardId, dto, auth);
        return new CmRespDto<>(1, "클럽게시판 수정완료", null);
    }

    @DeleteMapping("/delete/{clubBoardId}")
    public CmRespDto<?> deleteClubBoard(@PathVariable long clubBoardId, Authentication auth) {
        clubBoardService.delete(clubBoardId, auth);
        return new CmRespDto<>(1, "클럽게시판 삭제완료", null);
    }

}
