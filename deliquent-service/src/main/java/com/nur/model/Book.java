package com.nur.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
//    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private LocalDateTime promoDate;
//    @JsonFormat(pattern = "dd/MM/yyyy")
    @LastModifiedDate
    private LocalDateTime releaseDate;
}
