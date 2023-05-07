package com.nur.repository;

import com.nur.BookConstants;
import com.nur.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository
public class BookRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Book> findBookByName(String bookName){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(bookName));
        return mongoTemplate.find(query, Book.class);
    }

    public List<Book> searchByCriteria(Map<String, String> searchCriteriaMap){

        Query query = new Query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BookConstants.DATE_FORMAT);

        searchCriteriaMap.forEach((key, value)->{
            if (key.equalsIgnoreCase(BookConstants.PROMOTION_DATE)){
                query.addCriteria(Criteria.where(key).gte(LocalDateTime.parse(value, formatter).with(LocalTime.MIN)));
            }
            else if (key.equalsIgnoreCase(BookConstants.PUBLISHED_DATE)){
                query.addCriteria(Criteria.where(key).lte(LocalDateTime.parse(value, formatter).with(LocalTime.MAX)));
            }
            if (key.equalsIgnoreCase(BookConstants.PRICE)){
                query.addCriteria(Criteria.where(key).is(Double.valueOf(value)));
            }
            else {
                //query.addCriteria(Criteria.where(key).is(value));
                query.addCriteria(Criteria.where(key).is(Pattern.compile(value, Pattern.CASE_INSENSITIVE)));
            }
        });
        return mongoTemplate.find(query, Book.class);
    }
}
