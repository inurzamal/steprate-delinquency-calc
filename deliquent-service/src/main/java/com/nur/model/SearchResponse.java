package com.nur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private LocalDate promoDate;
    private LocalDate releaseDate;
}
