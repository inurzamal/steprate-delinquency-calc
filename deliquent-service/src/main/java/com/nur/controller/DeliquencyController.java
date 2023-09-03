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
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DeliquencyController {

    @Autowired
    private DelinquencyCalcService delinquencyCalcService;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @PostMapping(value = "/api/steprate/delinquency/v1/calculate", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<List<DelinquencyMonthRecord>> delinquencyCalculation(@RequestBody DelinquencyRequest delinquencyRequest){
        try {
            List<DelinquencyMonthRecord> delinquencyMonthlyRecords = delinquencyCalcService.getDelinquencyMonthlyRecords(delinquencyRequest);
//        List<DelinquencyMonthRecord> delinquencyMonthRecordList = delinquencyMonthlyRecords.stream().map(record -> mapToFormattedMonthlyRecords(record)).collect(Collectors.toList());
            List<DelinquencyMonthRecord> delinquencyMonthRecordList = delinquencyMonthlyRecords.stream().map(this:: mapToFormattedMonthlyRecords).collect(Collectors.toList());
            return new ResponseEntity<>(delinquencyMonthRecordList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private DelinquencyMonthRecord mapToFormattedMonthlyRecords(DelinquencyMonthRecord record) {
        DelinquencyMonthRecord monthRecord = new DelinquencyMonthRecord();
        monthRecord.setPrincipleBalance(record.getPrincipleBalance());
        monthRecord.setPrincipleAndInterest(record.getPrincipleAndInterest());
        monthRecord.setInterestRate(formatDecimal(record.getInterestRate() * 100));
        monthRecord.setDueDate(record.getDueDate());
        monthRecord.setInterest(record.getInterest());
        monthRecord.setPrinciple(record.getPrinciple());
        return monthRecord;
    }

//    private String formatDecimal(Double decimal, String pattern) {
//        DecimalFormat df = new DecimalFormat(pattern);
//        return df.format(df);
//    }

    private static double formatDecimal(double value){
        try {
            return Double.parseDouble(decimalFormat.format(value));
        }catch (NumberFormatException exception){
            return value;
        }
    }

}
