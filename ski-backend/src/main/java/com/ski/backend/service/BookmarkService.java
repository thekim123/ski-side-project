package com.ski.backend.service;

import java.util.List;

import com.ski.backend.repository.BookmarkRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.board.Bookmark;
import com.ski.backend.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	@Transactional
	public void onBookmark(long fromUserId, long toResortId) {
		bookmarkRepository.customOnBookmark(fromUserId, toResortId);
	}

	@Transactional
	public void offBookmark(long fromUserId, long toResortId) {
		bookmarkRepository.customOffBookmark(fromUserId, toResortId);
	}

	@Transactional(readOnly = true)
	public List<Bookmark> bookmarkList(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		User userEntity = principalDetails.getUser();
		List<Bookmark>bookmarks = bookmarkRepository.findByFromUser(userEntity);
		return bookmarks;
	}

}
