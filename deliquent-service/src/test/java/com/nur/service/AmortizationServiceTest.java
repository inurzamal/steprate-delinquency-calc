package com.nur.service;

import com.nur.model.AmortizationRequest;
import com.nur.model.AmortizationScheduleItem;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AmortizationServiceTest {

    private AmortizationService amortizationService;

    @BeforeEach
    void setup() {
        amortizationService = new AmortizationService();
    }

    //    @Test
//    void testCalculateAmortizationSchedule() {
//        // Test input parameters
//        double loanAmount = 200000;
//        double annualInterestRate = 4.5;
//        int loanTermInYears = 7;
//
//        // Perform the calculation
//        List<AmortizationScheduleItem> schedule = amortizationService.calculateAmortizationSchedule(
//                loanAmount, annualInterestRate, loanTermInYears);
//
//        // Assert the size of the schedule
//        assertEquals(loanTermInYears * 12, schedule.size());
//
//        // Assert the values of the first schedule item
//        AmortizationScheduleItem firstItem = schedule.get(0);
//        assertEquals(1, firstItem.getInstallmentNo());
//        // Assert other fields as needed
//    }

//    @Test
//    void testCalculateAmortizationSchedule() {
//        // Test input parameters
//        double loanAmount = 200000;
//        double annualInterestRate = 4.5;
//        int loanTermInYears = 7;
//
//        // Perform the calculation
//        List<AmortizationScheduleItem> schedule =
//                amortizationService.calculateAmortizationSchedule(new AmortizationRequest(loanAmount, annualInterestRate, loanTermInYears));
//
//        // Assert the size of the schedule
//        assertEquals(loanTermInYears * 12, schedule.size());
//
//        // Assert the values of the first schedule item
//        AmortizationScheduleItem firstItem = schedule.get(0);
//        assertEquals(1, firstItem.getInstallmentNo());
//        // Assert other fields as needed
//    }

    @Test
    void testCalculateAmortizationSchedule() {

        // Perform the calculation
    List<AmortizationScheduleItem> schedule =
            amortizationService.calculateAmortizationSchedule(Instancio.create(AmortizationRequest.class));

        // Assert the values of the first schedule item
        AmortizationScheduleItem firstItem = schedule.get(0);
        assertEquals(1, firstItem.getInstallmentNo());
        // Assert other fields as needed
    }
}
