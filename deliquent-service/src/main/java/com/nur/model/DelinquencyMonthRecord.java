package com.nur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelinquencyMonthRecord {
    private LocalDate dueDate;
    private double interest;
    private double principle;
    private double principleBalance;
    private double interestRate;
    private double principleAndInterest;
}
