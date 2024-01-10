package com.nur.repository;

import com.nur.BookConstants;
import com.nur.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository
public class BookRepositoryImpl {

    public static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

   public List<Book> findBookByName(String bookName){
        Query query = new Query();
        query.addCriteria(Criteria.where("bookTitle").is(Pattern.compile(bookName, Pattern.CASE_INSENSITIVE)));
        return mongoTemplate.find(query, Book.class);
    }

    public List<Book> searchByCriteria(Map<String, String> searchCriteriaMap){
        Query query = new Query();

        searchCriteriaMap.forEach((key, value) -> {
            if(key.equalsIgnoreCase(BookConstants.PROMOTION_DATE)){
//                query.addCriteria(Criteria.where(key).gte(LocalDateTime.parse(value, BookConstants.DATE_TIME_FORMATTER).with(LocalTime.MIN))); // date ranges with recent dates
                LocalDateTime dateTime = LocalDateTime.parse(value, BookConstants.DATE_TIME_FORMATTER);
                LocalDateTime startOfDay = dateTime.with(LocalTime.MIN);
                LocalDateTime endOfDay = dateTime.with(LocalTime.MAX);
                query.addCriteria(Criteria.where(key).gte(startOfDay).lte(endOfDay)); //exact date search
            }
            else if(key.equalsIgnoreCase(BookConstants.PUBLISHED_DATE)){
                query.addCriteria(Criteria.where(key).lte(LocalDateTime.parse(value, BookConstants.DATE_TIME_FORMATTER).with(LocalTime.MAX))); // older date upto the given date with maximum time of the day
            }
            else if(key.equalsIgnoreCase(BookConstants.PRICE)){
                query.addCriteria(Criteria.where(key).is(Double.valueOf(value)));
            }
            else{
                //query.addCriteria(Criteria.where(key).is(value));
                query.addCriteria(Criteria.where(key).is(Pattern.compile(value, Pattern.CASE_INSENSITIVE)));
            }
        });
//        return getByQuery(query);
        return mongoTemplate.find(query, Book.class);
    }

    private List<Book> getByQuery(Query query) {
       List<Book> bookModels = new ArrayList<>();
       try {
           bookModels = mongoTemplate.find(query, Book.class);
       }catch (Exception e){
           LOGGER.error(String.format("Error while retrieving the results for book with query: %s with exception : %s",
                   query.toString(),e.getMessage()));
       }
       return bookModels;
    }

}
