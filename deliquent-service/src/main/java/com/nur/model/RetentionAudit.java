package com.nur.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "retention_audit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetentionAudit {
    @Id
    private String id;
    private String tableName;
    private long recordCount;
    private LocalDateTime purgeTime;
}
