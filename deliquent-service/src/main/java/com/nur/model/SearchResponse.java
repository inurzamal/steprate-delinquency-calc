package com.nur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    @JsonProperty
    private String bookTitle;
    @JsonProperty
    private String author;
    @JsonProperty
    private String isbn;
    @JsonProperty
    private Double price;
    @JsonProperty
    private LocalDateTime promoDate;
    @JsonProperty
    private LocalDateTime releaseDate;
}
