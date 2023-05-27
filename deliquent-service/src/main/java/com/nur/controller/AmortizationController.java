package com.nur.controller;

import com.nur.model.AmortizationScheduleItem;
import com.nur.service.AmortizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/amortization")
public class AmortizationController {

    private final AmortizationService amortizationService;

    public AmortizationController(AmortizationService amortizationService) {
        this.amortizationService = amortizationService;
    }

    @GetMapping("/api/v1/calculate")
    public ResponseEntity<List<AmortizationScheduleItem>> calculateAmortization(
            @RequestParam("loanAmount") double loanAmount,
            @RequestParam("annualInterestRate") double annualInterestRate,
            @RequestParam("loanTermInYears") int loanTermInYears) {

        List<AmortizationScheduleItem> schedule = amortizationService.calculateAmortizationSchedule(
                loanAmount, annualInterestRate, loanTermInYears);

        return ResponseEntity.ok(schedule);
    }
}


