package com.nur.scheduler;

import com.nur.model.Book;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class DataRetentionTask {

    @Autowired
    private MongoTemplate mongoTemplate;

//    @Scheduled(cron = "0 0 1 * * *") // Runs every day at 1:00 AM
    @Scheduled(fixedDelay = 600000) // Runs every 10 minutes
    public void deleteOldData() {
//        LocalDateTime retentionDate = LocalDateTime.now().minus(30, ChronoUnit.DAYS); // Retain data for 30 days
        LocalDateTime retentionDate = LocalDateTime.now().minusMinutes(10); // Retain data for 10 minutes


        // Define your query to retrieve documents based on the retention policy
        Query query = new Query();
        query.addCriteria(Criteria.where("promoDate").lt(retentionDate));

        // Delete documents matching the query
        mongoTemplate.remove(query, Book.class);
    }
}
