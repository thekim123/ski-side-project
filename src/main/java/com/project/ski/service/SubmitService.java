package com.project.ski.service;

import com.project.ski.domain.carpool.Submit;
import com.project.ski.handler.ex.CustomApiException;
import com.project.ski.repository.SubmitRepository;
import com.project.ski.web.dto.AdmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitService {
    private final SubmitRepository submitRepository;

    @Transactional(readOnly = true)
    public List<Submit> getSubmit(long toCarpoolId) {
        List<Submit> submitEntity = submitRepository.findByToCarpoolId(toCarpoolId);
        return submitEntity;
    }

    @Transactional
    public void submit(long fromUserId, long toCarpoolId) {
        try {
            submitRepository.mSubmit(fromUserId, toCarpoolId);
        } catch (Exception e) {
            throw new CustomApiException("이미 제출을 하셨습니다.");
        }
    }

    @Transactional
    public void unSubmit(long fromUserId, long toCarpoolId) {
        submitRepository.mUnSubmit(fromUserId, toCarpoolId);
    }

    @Transactional
    public void admit(AdmitDto dto){
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId());
        submitEntity.setState(1);
    }

    @Transactional
    public void deleteAdmit(AdmitDto dto){
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId());
        submitRepository.delete(submitEntity);
        submitEntity.setState(-1);
    }
}
