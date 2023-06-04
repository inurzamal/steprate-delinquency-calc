package com.nur.controller;

import com.nur.model.AmortizationRequest;
import com.nur.model.AmortizationScheduleItem;
import com.nur.service.AmortizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amortization")
public class AmortizationController {

    private final AmortizationService amortizationService;

    public AmortizationController(AmortizationService amortizationService) {
        this.amortizationService = amortizationService;
    }

//    @GetMapping("/api/v1/calculate")
//    public ResponseEntity<List<AmortizationScheduleItem>> calculateAmortization(
//            @RequestParam(value="loanAmount", required = false) double loanAmount,
//            @RequestParam(value="annualInterestRate", required = false) double annualInterestRate,
//            @RequestParam(value="loanTermInYears", required = false) int loanTermInYears) {
//
//        List<AmortizationScheduleItem> schedule = amortizationService.calculateAmortizationSchedule(
//                loanAmount, annualInterestRate, loanTermInYears);
//
//        return ResponseEntity.ok(schedule);
//    }

    @PostMapping("/api/v1/calculate")
    public ResponseEntity<List<AmortizationScheduleItem>> calculateAmortization(@RequestBody AmortizationRequest amortizationRequest) {

        List<AmortizationScheduleItem> schedule = amortizationService.calculateAmortizationSchedule(amortizationRequest);

        return ResponseEntity.ok(schedule);
    }
}


