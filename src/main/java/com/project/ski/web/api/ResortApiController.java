package com.project.ski.web.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ski.domain.resort.Resort;
import com.project.ski.service.ResortService;
import com.project.ski.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/resort")
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

    @PostMapping("/insert")
    public void insertResort(@RequestBody Resort resort){
        resortService.insert(resort);
    }

}
