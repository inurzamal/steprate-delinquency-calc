package com.nur.scheduler;

import com.nur.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class DataRetentionTask {
    private MongoTemplate mongoTemplate;
    private DataRetentionConfig dataRetentionConfig;

/*
*
    @Scheduled(cron = "0 0 1 * * *") // Runs every day at 1:00 AM
    @Scheduled(cron = "0 30 11 * * *") // Runs every day at 11:30 AM
    @Scheduled(cron = "0 0 14 * * *") // Runs every day at 2:00 PM
    @Scheduled(cron = "${scheduled-time}")
    @Scheduled(fixedDelay = 300000) // Runs every 5 minutes (5*1000*60)
*
 */

//    @Scheduled(cron = "${scheduled-time}")
    @Scheduled(fixedDelayString = "${scheduled-time}")
    public void deleteOldData() {
//        LocalDateTime retentionDate = LocalDateTime.now().minus(30, ChronoUnit.DAYS); // Retain data for 30 days
//        LocalDateTime retentionDate = LocalDateTime.now().minusYears(3); // Retain data for 3 years
//        LocalDateTime retentionDate = LocalDateTime.now().minusMinutes(10); // Retain data for 10 minutes

//        LocalDateTime retentionDate = LocalDateTime.now().minus(dataRetentionConfig.getAdm100());
        LocalDateTime retentionDate = LocalDateTime.now().minus(dataRetentionConfig.getRetentionPeriod());


        // Define your query to retrieve documents based on the retention policy
        Query query = new Query();
        query.addCriteria(Criteria.where("promoDate").lt(retentionDate));

        // Delete documents matching the query
        mongoTemplate.remove(query, Book.class);
    }
}
