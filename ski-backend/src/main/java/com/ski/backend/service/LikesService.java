package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.board.Likes;
import com.ski.backend.repository.DislikesRepository;
import com.ski.backend.repository.LikesRepository;
import com.ski.backend.web.dto.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final DislikesRepository dislikesRepository;


    @Transactional
    public void like(LikesDto dto, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        long boardId = dto.getBoardId();
        likesRepository.mLike(boardId, principalId);

        if (dto.isDislikeState() == true) {
            dislikesRepository.mUnDislike(boardId, principalId);
        }
    }

    @Transactional
    public void unlike(LikesDto dto, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        long boardId = dto.getBoardId();
        likesRepository.mUnlike(boardId, principalId);
    }

    @Transactional
    public void dislike(LikesDto dto, Authentication authentication) {

        long principalId = getPrincipalId(authentication);
        long boardId = dto.getBoardId();

        dislikesRepository.mDislike(boardId, principalId);

        if (dto.isLikeState() == true) {
            likesRepository.mUnlike(boardId, principalId);
        }
    }

    @Transactional
    public void unDislike(LikesDto dto, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        long boardId = dto.getBoardId();

        dislikesRepository.mUnDislike(boardId, principalId);
    }

    public long getPrincipalId(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        return principalId;
    }


}
