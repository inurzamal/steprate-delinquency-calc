package com.nur.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate promoDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;
}
