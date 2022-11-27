package com.project.ski.service;

import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.resort.ResortName;
import com.project.ski.repository.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ResortService {

    private final ResortRepository resortRepository;

    @Transactional
    public void insert(Resort resort) {
        resortRepository.save(resort);
    }

    // 임시 서비스(오픈하면 삭제)
    // 리조트 일일히 등록하기 귀찮아서 만듬
    @Transactional
    public void autoInsert() {
        String[] arr = {"엘리시안", "휘닉스파크", "비발디파크", "용평리조트", "하이원리조트", "베어스타운", "곤지암리조트", "지산포레스트", "웰리힐리", "오크벨리", "덕유산리조트", "에덴벨리"};
        if (resortRepository.findAll().size() == 0) {
            for (String resortName : arr) {
                Resort resort = new Resort();
                resort.setResortName(ResortName.valueOf(resortName));
                resortRepository.save(resort);
            }
        }
    }
}
