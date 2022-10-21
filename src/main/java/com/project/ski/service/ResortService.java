package com.project.ski.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.resort.Weather;
import com.project.ski.repository.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ResortService {

    @Value("${weather.key}")
    private String weatherMapKey;
    private final ResortRepository resortRepository;

    //37°48'59.0"N 127°35'13.3"E
    @Transactional
    public Resort getWeather(Resort resort) throws JsonProcessingException {
        String apiUrl = resort.buildApiUrl(weatherMapKey);
        Weather weather = new Weather().buildWeather(apiUrl);
        weather.setResortName(resort.getResortName());

        resort.setWeather(weather);
        resortRepository.save(resort);
        return resort;
    }

    @Transactional
    public void insert(Resort resort) {
        System.out.println(resort.getResortName());
        resortRepository.save(resort);
    }
}
