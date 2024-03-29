package com.nur.scheduler;


import com.mongodb.client.result.DeleteResult;
import com.nur.model.Book;
import com.nur.model.RetentionAudit;
import com.nur.repository.RetentionAuditRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DataRetentionTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRetentionTask.class);
    public static final String TABLE_NAME = "books";
    private MongoTemplate mongoTemplate;
    private DataRetentionConfig dataRetentionConfig;
    private RetentionAuditRepository auditRepository;

/*
*
    @Scheduled(cron = "0 0 1 * * *") // Runs every day at 1:00 AM
    @Scheduled(cron = "0 30 11 * * *") // Runs every day at 11:30 AM
    @Scheduled(cron = "0 0 14 * * *") // Runs every day at 2:00 PM
    @Scheduled(cron = "${scheduled-time}")
    @Scheduled(fixedDelayString = "${scheduled-time}")
    @Scheduled(fixedDelay = 300000) // Runs every 5 minutes (5*1000*60)
*
 */

    @Scheduled(cron = "${scheduled-time}")
    public void deleteOldData() {

        try {
//        LocalDateTime retentionDate = LocalDateTime.now().minusYears(3); // Retain data for 3 years
//        LocalDateTime retentionDate = LocalDateTime.now().minusMinutes(10); // Retain data for 10 minutes

            LocalDateTime retentionDate = LocalDateTime.now().minusMinutes(dataRetentionConfig.getRetentionPeriod());

            // Define your query to retrieve documents based on the retention policy
            Query query = new Query();
            query.addCriteria(Criteria.where("promoDate").lt(retentionDate));

            // Delete documents matching the query
            DeleteResult deleteResult = mongoTemplate.remove(query, Book.class);
            long deletedCount = deleteResult.getDeletedCount();
            LOGGER.info("Deletion of old data completed. Number of records deleted: {}", deletedCount);

            if(deletedCount>0){
                createRetentionAudit(TABLE_NAME, deletedCount);
            }
        } catch (DataAccessException e) {
            // Handle data access-related exceptions (e.g., MongoDB connection issues, query errors)
            LOGGER.error("Error while deleting old data: {}", e.getMessage(), e);
        } catch (Exception e) {
            // Handle the exception
            LOGGER.error("An error occurred while deleting old data: {}", e.getMessage(), e);
            // You can choose to rethrow the exception if needed or perform other error handling logic.
        }
    }

    private void createRetentionAudit(String tableName, long deletedCount) {
        try {
            RetentionAudit retentionAudit = new RetentionAudit();
            retentionAudit.setRecordCount(deletedCount);
            retentionAudit.setTableName(tableName);
            retentionAudit.setPurgeTime(LocalDateTime.now());
            auditRepository.save(retentionAudit);

            deleteDestructionLogs();

        } catch (DataAccessException e) {
            LOGGER.error("Error while creating retention audit: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred while creating retention audit: {}", e.getMessage(), e);
        }
    }

    private void deleteDestructionLogs() {
        LocalDateTime retentionDate = LocalDateTime.now().minusMinutes(dataRetentionConfig.getAdm100());
        LOGGER.info("Srating deletion of old data from destructionLogs...");
        Query query = new Query();
        query.addCriteria(Criteria.where("purgeTime").lt(retentionDate));
        DeleteResult deleteResult = mongoTemplate.remove(query, RetentionAudit.class);
        LOGGER.info("Number of document deleted from destructionLogs: {}",deleteResult.getDeletedCount());
    }
}
