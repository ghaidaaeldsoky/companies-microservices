package com.companies.service_fees.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fees")
public class FeesInitializationController {
    
    @GetMapping
    public ResponseEntity<BigDecimal> getFee(@RequestParam String serviceId){
        return ResponseEntity.status(200).body(new BigDecimal(200.00));
    }

}
