package com.project.ski.service;

import com.project.ski.domain.resort.Resort;
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
        System.out.println(resort.getResortName());
        resortRepository.save(resort);
    }
}
