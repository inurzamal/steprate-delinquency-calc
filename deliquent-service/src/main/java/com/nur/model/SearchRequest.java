package com.nur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String title;
    private String author;
    private String isbn;
    private String price;
    private String promoDate;
    private String releaseDate;
}
