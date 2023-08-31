package com.nur.service;

import com.nur.model.DelinquencyMonthRecord;
import com.nur.model.DelinquencyRequest;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DelinquencyCalcService {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public List<DelinquencyMonthRecord> getDelinquencyMonthlyRecords(DelinquencyRequest delinquencyRequest){
        List<DelinquencyMonthRecord> records = new ArrayList<>();

        DelinquencyMonthRecord previousRecord = calculateFirstMonthRecord(delinquencyRequest);
        records.add(previousRecord);

        while (previousRecord.getPrincipleBalance() > 0 && records.size() < 84){
            DelinquencyMonthRecord nextRecord = new DelinquencyMonthRecord();
            nextRecord.setPrincipleBalance(formatDecimal(previousRecord.getPrincipleBalance()));
            nextRecord.setPrincipleAndInterest(formatDecimal(previousRecord.getPrincipleAndInterest()));
            nextRecord.setInterestRate(formatDecimal(previousRecord.getInterestRate()));
            nextRecord.setDueDate(previousRecord.getDueDate().plusMonths(1));

            nextRecord.setInterest(formatDecimal(nextRecord.getPrincipleBalance() * nextRecord.getInterestRate() / 12));
            nextRecord.setPrinciple(formatDecimal(nextRecord.getPrincipleAndInterest() - nextRecord.getInterest()));
            nextRecord.setPrincipleBalance(formatDecimal(nextRecord.getPrincipleBalance() - nextRecord.getPrinciple()));
            records.add(nextRecord);
            previousRecord = nextRecord;
        }
        return records;
    }

    private static DelinquencyMonthRecord calculateFirstMonthRecord(DelinquencyRequest delinquencyRequest) {
        DelinquencyMonthRecord firstRecord = new DelinquencyMonthRecord();
        firstRecord.setPrincipleBalance(formatDecimal(delinquencyRequest.getPrincipleBalance()));
        firstRecord.setPrincipleAndInterest(formatDecimal(delinquencyRequest.getPrincipleAndInterest()));
        firstRecord.setInterestRate(delinquencyRequest.getInterestRate());
        firstRecord.setDueDate(delinquencyRequest.getCurrentDueDate());

        firstRecord.setInterest(formatDecimal(firstRecord.getPrincipleBalance() * firstRecord.getInterestRate() / 12));
        firstRecord.setPrinciple(formatDecimal(firstRecord.getPrincipleAndInterest() - firstRecord.getInterest()));
        firstRecord.setPrincipleBalance(formatDecimal(firstRecord.getPrincipleBalance() - firstRecord.getPrinciple()));
        return firstRecord;
    }

    private static double formatDecimal(double value){
        try {
            return Double.parseDouble(decimalFormat.format(value));
        }catch (NumberFormatException exception){
            return value;
        }
    }
}
