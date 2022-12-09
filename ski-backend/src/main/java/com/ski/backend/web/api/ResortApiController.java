package com.ski.backend.web.api;


import com.ski.backend.domain.resort.Resort;
import com.ski.backend.service.ResortService;
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
