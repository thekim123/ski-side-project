package com.project.ski.service;

import com.project.ski.domain.carpool.Submit;
import com.project.ski.handler.ex.CustomApiException;
import com.project.ski.repository.SubmitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitService {
    private final SubmitRepository submitRepository;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<Submit> getSubmit(long toCarpoolId) {
        List<Submit> submitEntity = submitRepository.findByToCarpoolId(toCarpoolId);
        return submitEntity;
    }

    @Transactional
    public void submit(long fromUserId, long toUserId) {
        try {
            submitRepository.mSubmit(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 제출을 하셨습니다.");
        }
    }

    @Transactional
    public void unSubmit(long fromUserId, long toUserId) {
        submitRepository.mUnSubmit(fromUserId, toUserId);
    }
}
