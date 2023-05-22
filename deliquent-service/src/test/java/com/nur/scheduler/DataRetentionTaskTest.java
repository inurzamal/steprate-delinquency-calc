package com.nur.scheduler;

import com.nur.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DataRetentionTaskTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private DataRetentionConfig dataRetentionConfig;

    @InjectMocks
    private DataRetentionTask dataRetentionTask;

    @Test
    public void testDeleteOldData() {
        // Set up the test case
//        LocalDateTime retentionDate = LocalDateTime.now().minus(3, ChronoUnit.YEARS);
//        Mockito.when(dataRetentionConfig.getRetentionPeriod()).thenReturn(Duration.ofSeconds(3 * 365 * 24 * 60 * 60));
        Mockito.when(dataRetentionConfig.getRetentionPeriod()).thenReturn(Duration.ofMinutes(3 * 365 * 24 * 60 ));


        // Execute the method to be tested
        dataRetentionTask.deleteOldData();

        // Verify the expected behavior
        Mockito.verify(mongoTemplate).remove(Mockito.any(), Mockito.eq(Book.class));
    }
}
