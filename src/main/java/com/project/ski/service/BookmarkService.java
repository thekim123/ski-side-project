package com.project.ski.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Bookmark;
import com.project.ski.domain.user.User;
import com.project.ski.repository.BookmarkRepository;

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
