package com.liza.smart.diagnosis.controller;

import com.liza.smart.diagnosis.dto.BrainStockDetectionRequest;
import com.liza.smart.diagnosis.service.DiagonosisApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("diagnosis")
@RequiredArgsConstructor
public class DiagonosisController {
    private  final DiagonosisApiService diagonosisApiService;

    @PostMapping
    public boolean detedBrainStock(@RequestBody BrainStockDetectionRequest request){
       return  diagonosisApiService.stockDetectionRequest(request);
    }
}
