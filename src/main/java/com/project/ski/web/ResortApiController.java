package com.project.ski.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ski.domain.resort.Resort;
import com.project.ski.service.ResortService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ResortApiController {

    private final ResortService resortService;

    @GetMapping("/weather")
    public Resort getWeather(@RequestBody Resort resort) {
        try {
           resort = resortService.getWeather(resort);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resort;
    }

}
