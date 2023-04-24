package com.ski.backend.board.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.board.repository.DislikesRepository;
import com.ski.backend.board.repository.LikesRepository;
import com.ski.backend.board.dto.LikesDto;
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

        if (dto.isDislikeState()) {
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

        if (dto.isLikeState()) {
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
        return principalDetails.getUser().getId();
    }


}
