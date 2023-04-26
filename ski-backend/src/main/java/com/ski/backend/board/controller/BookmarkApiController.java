package com.ski.backend.board.controller;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.board.service.BookmarkService;
import com.ski.backend.common.CmRespDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkApiController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{toResortId}")
    public CmRespDto<?> onBookmark(Authentication authentication, @PathVariable long toResortId) {
        bookmarkService.onBookmark(authentication, toResortId);
        return new CmRespDto<>(1, "즐겨찾기 성공", null);
    }

    @DeleteMapping("/{toResortId}")
    public CmRespDto<?> offBookmark(Authentication authentication, @PathVariable long toResortId) {
        bookmarkService.offBookmark(authentication, toResortId);
        return new CmRespDto<>(1, "즐겨찾기 취소 성공", null);
    }

    @GetMapping("/mine")
    public CmRespDto<?> bookmarkList(Authentication authentication) {
        List<?> bookmarks = bookmarkService.bookmarkList(authentication);
        return new CmRespDto<>(1, "즐겨찾기한 리조트 조회 성공", bookmarks);
    }

}
