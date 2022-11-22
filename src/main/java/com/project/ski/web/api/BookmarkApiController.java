package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.service.BookmarkService;
import com.project.ski.web.dto.CmRespDto;
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
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		long fromUserId = principalDetails.getUser().getId();
		System.out.println(fromUserId);
		bookmarkService.onBookmark(fromUserId, toResortId);
		return new CmRespDto<>(1, "즐겨찾기 성공", null);
	}

	@DeleteMapping("/{toResortId}")
	public CmRespDto<?> offBookmark(Authentication authentication, @PathVariable long toResortId) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		long principalId = principalDetails.getUser().getId();
		bookmarkService.offBookmark(principalId, toResortId);
		return new CmRespDto<>(1, "즐겨찾기 취소 성공", null);
	}

	@GetMapping("/mine")
	public CmRespDto<?> bookmarkList(Authentication authentication) {
		List<?> bookmarks = bookmarkService.bookmarkList(authentication);
		return new CmRespDto<>(1, "즐겨찾기한 리조트 조회 성공", bookmarks);
	}

}
