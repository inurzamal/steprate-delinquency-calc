package com.nur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationRequest {
    private double loanAmount;
    private double annualInterestRate;
    private int loanTermInYears;
}
