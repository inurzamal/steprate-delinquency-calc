package com.nur.scheduler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetentionAuditRepository extends MongoRepository<RetentionAudit, String> {
}
