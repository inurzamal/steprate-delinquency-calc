package com.nur.controller;

import com.nur.model.AmortizationScheduleItem;
import com.nur.service.AmortizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AmortizationControllerTest {

    @InjectMocks
    private AmortizationController amortizationController;

    @Mock
    private AmortizationService amortizationService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(amortizationController).build();
    }

    @Test
    void testCalculateAmortization() throws Exception {
        // Test input parameters
        double loanAmount = 520000;
        double annualInterestRate = 8.3;
        int loanTermInYears = 7;

        // Expected result
        List<AmortizationScheduleItem> expectedSchedule = new ArrayList<>();

            AmortizationScheduleItem item = new AmortizationScheduleItem();
            // Set values for the expected schedule item
            item.setInstallmentNo(1);
            item.setDueDate(LocalDate.now().plusMonths(1));
            item.setInstallmentAmount(8182.7);
            item.setPrincipal(4586.1);
            item.setInterest(3596.6);
            item.setOutstandingPrincipal(515413.8);
            expectedSchedule.add(item);


        // Mock the service method
        when(amortizationService.calculateAmortizationSchedule(loanAmount, annualInterestRate, loanTermInYears))
                .thenReturn(expectedSchedule);

        // Perform the API request and assertions
        mockMvc.perform(get("/amortization/api/v1/calculate")
                        .param("loanAmount", String.valueOf(loanAmount))
                        .param("annualInterestRate", String.valueOf(annualInterestRate))
                        .param("loanTermInYears", String.valueOf(loanTermInYears)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedSchedule.size())))
                .andExpect(jsonPath("$[0].installmentNo", is(expectedSchedule.get(0).getInstallmentNo())))
//                .andExpect(jsonPath("$[0].dueDate", is(expectedSchedule.get(0).getDueDate().toString())))
                .andExpect(jsonPath("$[0].installmentAmount", is(expectedSchedule.get(0).getInstallmentAmount())))
                .andExpect(jsonPath("$[0].principal", is(expectedSchedule.get(0).getPrincipal())))
                .andExpect(jsonPath("$[0].interest", is(expectedSchedule.get(0).getInterest())))
                .andExpect(jsonPath("$[0].outstandingPrincipal", is(expectedSchedule.get(0).getOutstandingPrincipal())));

        // Verify the service method was called
        verify(amortizationService).calculateAmortizationSchedule(loanAmount, annualInterestRate, loanTermInYears);
    }
}
