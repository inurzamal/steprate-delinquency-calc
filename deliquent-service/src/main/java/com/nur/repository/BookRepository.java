package com.nur.repository;

import com.nur.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

//    List findByAuthor(String name);
//    List findByTitleContaining(String title);
}
