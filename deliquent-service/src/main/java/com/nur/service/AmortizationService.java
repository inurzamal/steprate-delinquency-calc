package com.nur.service;

import com.nur.model.AmortizationRequest;
import com.nur.model.AmortizationScheduleItem;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmortizationService {

    public static final int MONTHS_IN_YEAR = 12;

//    public List<AmortizationScheduleItem> calculateAmortizationSchedule(double loanAmount, double annualInterestRate, int loanTermInYears) {
//        double monthlyInterestRate = (annualInterestRate / 100) / MONTHS_IN_YEAR;
//        int numberOfPayments = loanTermInYears * MONTHS_IN_YEAR;
//        double monthlyPayment = calculateMonthlyPayment(loanAmount, monthlyInterestRate, numberOfPayments);
//
//        List<AmortizationScheduleItem> schedule = new ArrayList<>();
//
//        double remainingBalance = loanAmount;
//
//        for (int installmentNo = 1; installmentNo <= numberOfPayments; installmentNo++) {
//            double interestPayment = remainingBalance * monthlyInterestRate;
//            double principalPayment = monthlyPayment - interestPayment;
//            remainingBalance -= principalPayment;
//
//            LocalDate dueDate = LocalDate.now().plusMonths(installmentNo);
//
//            AmortizationScheduleItem scheduleItem = new AmortizationScheduleItem();
//            scheduleItem.setInstallmentNo(installmentNo);
//            scheduleItem.setDueDate(dueDate);
//            scheduleItem.setInstallmentAmount(monthlyPayment);
//            scheduleItem.setPrincipal(principalPayment);
//            scheduleItem.setInterest(interestPayment);
//            scheduleItem.setOutstandingPrincipal(remainingBalance);
//
//            schedule.add(scheduleItem);
//        }
//
//        return schedule;
//    }
public List<AmortizationScheduleItem> calculateAmortizationSchedule(AmortizationRequest amortizationRequest) {
    double monthlyInterestRate = (amortizationRequest.getAnnualInterestRate() / 100) / MONTHS_IN_YEAR;
    int numberOfPayments = amortizationRequest.getLoanTermInYears() * MONTHS_IN_YEAR;
    double monthlyPayment = calculateMonthlyPayment(amortizationRequest.getLoanAmount(), monthlyInterestRate, numberOfPayments);

    List<AmortizationScheduleItem> schedule = new ArrayList<>();

    double remainingBalance = amortizationRequest.getLoanAmount();

    for (int installmentNo = 1; installmentNo <= numberOfPayments; installmentNo++) {
        double interestPayment = remainingBalance * monthlyInterestRate;
        double principalPayment = monthlyPayment - interestPayment;
        remainingBalance -= principalPayment;

        LocalDate dueDate = LocalDate.now().plusMonths(installmentNo);

        AmortizationScheduleItem scheduleItem = new AmortizationScheduleItem();
        scheduleItem.setInstallmentNo(installmentNo);
        scheduleItem.setDueDate(dueDate);
        scheduleItem.setInstallmentAmount(monthlyPayment);
        scheduleItem.setPrincipal(principalPayment);
        scheduleItem.setInterest(interestPayment);
        scheduleItem.setOutstandingPrincipal(remainingBalance);

        schedule.add(scheduleItem);
    }

    return schedule;
}

    /*
     *  M = P * r * (1 + r)^n) / ((1 + r)^n - 1)
     *
     * Where:
        M = Monthly mortgage payment
        P = Principal loan amount
        r = Monthly interest rate (annual interest rate divided by 12)
        n = Total number of monthly payments (loan term in months)
     */
    private double calculateMonthlyPayment(double loanAmount, double monthlyInterestRate, int numberOfPayments) {
        double numerator = loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments);
        double denominator = Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1;
        return numerator / denominator;
    }
}


