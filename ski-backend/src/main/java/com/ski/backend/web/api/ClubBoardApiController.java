package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.user.User;
import com.ski.backend.service.ClubBoardService;
import com.ski.backend.web.dto.ClubBoardDto;
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
    public CmRespDto<ClubBoardDto> create(@Valid @RequestBody ClubBoardDto dto, BindingResult bindingResult, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        ClubBoardDto clubBoard = clubBoardService.createClubBoard(dto, user);
        return new CmRespDto<>(1, "클럽게시판 생성 완료", clubBoard);
    }

    @GetMapping("/detail/{clubBoardId}")
    public CmRespDto<Page<ClubBoardDto>> getClubBoard(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, @PathVariable long clubBoardId) {
        Page<ClubBoardDto> clubBoard = clubBoardService.getClubBoard(pageable, clubBoardId);
        return new CmRespDto<>(1, "클럽게시판 조회 완료", clubBoard);
    }

    @GetMapping("/{clubId}")
    public CmRespDto<Page<ClubBoardDto>> getAllClubBoard(@PageableDefault(sort = "id", direction = DESC) Pageable pageable, @PathVariable long clubId) {
        Page<ClubBoardDto> clubBoardDtos = clubBoardService.getAllClubBoard(pageable, clubId);
        return new CmRespDto<>(1, "클럽게시판 전체조회 완료", clubBoardDtos);
    }

    @PutMapping("/update/{clubBoardId}")
    public CmRespDto<ClubBoardDto> updateClubBoard(@PathVariable long clubBoardId, @Valid @RequestBody ClubBoardDto dto, BindingResult bindingResult, Authentication auth) {
        clubBoardService.update(clubBoardId, dto, auth);
        return new CmRespDto<>(1, "클럽게시판 수정완료", null);
    }

    @DeleteMapping("/delete/{clubBoardId}")
    public CmRespDto<ClubBoardDto> deleteClubBoard(@PathVariable long clubBoardId, Authentication auth) {
        clubBoardService.delete(clubBoardId, auth);
        return new CmRespDto<>(1, "클럽게시판 삭제완료", null);
    }

    // 관리자가 - 회원->매니저 권한 주기
    @PutMapping("/updateRole/{clubBoardId}/{userId}/{roleYn}")
    public CmRespDto updateRole(@PathVariable long clubBoardId, @PathVariable long userId, Authentication auth, @PathVariable boolean roleYn) {
        return clubBoardService.updateRole(clubBoardId, userId, auth, roleYn);

    }
}
