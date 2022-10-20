package com.project.ski.service;

import com.project.ski.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void like(long boardId, long principalId) {
        likesRepository.mLike(boardId, principalId);
    }

    @Transactional
    public void unlike(long boardId, long principalId) {
        likesRepository.mUnlike(boardId, principalId);
    }

}
