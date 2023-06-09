package com.nur.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelinquencyRequest {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate currentDueDate;
    private double principleBalance;
    private double interestRate;
    private double principleAndInterest;
}
