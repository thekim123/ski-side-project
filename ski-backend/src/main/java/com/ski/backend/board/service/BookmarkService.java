package com.ski.backend.board.service;

import java.util.List;

import com.ski.backend.board.repository.BookmarkRepository;
import com.ski.backend.resort.entity.Resort;
import com.ski.backend.resort.repository.ResortRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.board.entity.Bookmark;
import com.ski.backend.user.entity.User;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

/**
 * @author thekim123
 * @apiNote 즐겨찾기 서비스
 * @since ski-1.01
 */
@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ResortRepository resortRepository;

    /**
     * @param authentication 로그인 정보
     * @param toResortId     즐겨찾기 하고자 하는 리조트의 id
     * @apiNote 즐겨찾기 추가 서비스 로직 <br/>
     * 모디파잉 쿼리에서 jpa 네이밍 쿼리로 바꿈
     * @since last modified at 2023.04.26
     */
    @Transactional
    public void onBookmark(Authentication authentication, long toResortId) {
        Resort toResort = resortRepository.findById(toResortId).orElseThrow(() -> {
            throw new EntityNotFoundException("존재하지 않는 리조트입니다.");
        });

        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        Bookmark bookmark = Bookmark.builder()
                .fromUser(user)
                .toResort(toResort)
                .build();
        bookmarkRepository.save(bookmark);
    }

    /**
     * @param authentication 로그인 정보
     * @param toResortId     즐겨찾기 하고자 하는 리조트의 id
     * @apiNote 즐겨찾기 삭제 서비스 로직 <br/>
     * 모디파잉 쿼리에서 jpa 네이밍 쿼리로 바꿈
     * @since last modified at 2023.04.26
     */
    @Transactional
    public void offBookmark(Authentication authentication, long toResortId) {
        Resort toResort = resortRepository.findById(toResortId).orElseThrow(() -> {
            throw new EntityNotFoundException("존재하지 않는 리조트입니다.");
        });
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        bookmarkRepository.deleteByToResortAndFromUser(toResort, user);
    }

    /**
     * @param authentication 로그인 정보
     * @return 즐겨찾기 총 리스트
     * @apiNote 오로지 한 엔티티만 조회하고,
     * readOnly=true 여서 엔티티를 컨트롤러에 반환한다. <br/>
     * 근데 즐겨찾기는 개수가 한정되어 있으니까 dto 에 전부 매핑하는게 더 바람직해 보인다.
     */
    @Transactional(readOnly = true)
    public List<Bookmark> bookmarkList(Authentication authentication) {
        User userEntity = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        return bookmarkRepository.findByFromUser(userEntity);
    }

}
