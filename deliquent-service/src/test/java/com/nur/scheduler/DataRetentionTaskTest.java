package com.nur.scheduler;

import com.mongodb.client.result.DeleteResult;
import com.nur.model.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DataRetentionTaskTest {

    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private DataRetentionConfig dataRetentionConfig;
    @Captor
    private ArgumentCaptor<String> logMessageCaptor;
    @InjectMocks
    private DataRetentionTask dataRetentionTask;

    @Test
    @Disabled
    void deleteOldDataTest() {
        // Arrange
        LocalDateTime retentionDate = LocalDateTime.now().minusMinutes(1);
        Query query = new Query();
        query.addCriteria(Criteria.where("promoDate").lt(retentionDate));

        when(dataRetentionConfig.getRetentionPeriod()).thenReturn(1L);

        DeleteResult deleteResult = new DeleteResult() {
            @Override
            public boolean wasAcknowledged() {
                return true;
            }
            @Override
            public long getDeletedCount() {
                return 2;
            }
        };
        when(mongoTemplate.remove(query, Book.class)).thenReturn(deleteResult);
//        doReturn(deleteResult).when(mongoTemplate).remove(query, Book.class);

        // Act
        dataRetentionTask.deleteOldData();

        // Assert
//        verify(mongoTemplate).remove(query, Book.class);
        Mockito.verify(mongoTemplate).remove(Mockito.any(), Mockito.eq(Book.class));
    }

}