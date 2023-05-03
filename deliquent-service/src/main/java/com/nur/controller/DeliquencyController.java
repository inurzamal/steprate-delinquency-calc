package com.nur.controller;

import com.nur.model.DelinquencyMonthRecord;
import com.nur.model.DelinquencyRequest;
import com.nur.service.DelinquencyCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliquencyController {

    @Autowired
    private DelinquencyCalcService delinquencyCalcService;

    @PostMapping(value = "/api/steprate/delinquency/v1/calculate", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<List<DelinquencyMonthRecord>> delinquencyCalculation(@RequestBody DelinquencyRequest delinquencyRequest){
        List<DelinquencyMonthRecord> delinquencyMonthlyRecords = delinquencyCalcService.getDelinquencyMonthlyRecords(delinquencyRequest);
        return new ResponseEntity<>(delinquencyMonthlyRecords, HttpStatus.OK);
    }
}
