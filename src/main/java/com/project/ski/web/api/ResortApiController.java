package com.project.ski.web.api;


import com.project.ski.domain.resort.Resort;
import com.project.ski.service.ResortService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/resort")
public class ResortApiController {

    private final ResortService resortService;

    @PostMapping("/insert")
    public void insertResort(@RequestBody Resort resort){
        resortService.insert(resort);
    }

    @PostConstruct
    public void autoInsert(){
        resortService.autoInsert();
    }

}
