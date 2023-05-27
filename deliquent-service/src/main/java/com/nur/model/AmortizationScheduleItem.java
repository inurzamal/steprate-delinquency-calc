package com.nur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmortizationScheduleItem {

    private int installmentNo;
    private LocalDate dueDate;
    private double installmentAmount;
    private double principal;
    private double interest;
    private double outstandingPrincipal;

}

