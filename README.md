Data Retention Process Logic with Destruction Logs Deletion:

Initialization:

The data retention and destruction logs deletion process begins at the scheduled time specified in the configuration.
Calculation of Retention Date:

The current date and time are obtained using LocalDateTime.now().
The configured retention period, obtained from dataRetentionConfig, is subtracted from the current date and time. This determines the retention date, which serves as the cutoff point for data to be retained.
Data Deletion:

A MongoDB query is constructed to identify documents in the collection that have a promoDate field less than the calculated retention date.
The mongoTemplate is used to execute the query and delete the matching documents from the MongoDB collection.
The DeleteResult object obtained from the deletion operation is used to retrieve the count of deleted documents.
Audit and Logging:

If any documents were deleted (deletedCount > 0), a retention audit record is created to track the data removal.
The createRetentionAudit method is called to create a RetentionAudit object with details of the deleted count, table name, and the current date and time.
The created RetentionAudit object is saved using auditRepository.
Destruction Logs Deletion:

Upon successful creation of the retention audit record, the deleteDestructionLogs method is called.
This method calculates a retention date for the destruction logs based on the configured adm100 retention period.
A MongoDB query is constructed to identify RetentionAudit documents in the collection that have a purgeTime field older than the calculated retention date.
The mongoTemplate is used to execute the query and delete the matching documents from the MongoDB collection.
The DeleteResult object obtained from the deletion operation is used to retrieve the count of deleted documents.
Exception Handling:

Comprehensive exception handling is in place to gracefully handle potential errors during data deletion, audit creation, and destruction logs deletion.
Catch blocks are provided for both DataAccessException and generic Exception in each method. These blocks log error messages along with exception details to facilitate troubleshooting.
