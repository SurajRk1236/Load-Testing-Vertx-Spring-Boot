package com.vertx.custom.load.testing.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

import static com.vertx.custom.load.testing.constants.CollectionName.LOAD_TEST_COLLECTION;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = LOAD_TEST_COLLECTION)
public class LoadTest {

    @MongoId
    String id;

    String loadTestName;

    String curl;

    int noOfRequest;

    int noOfUsers;

    boolean completedStatus;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

}
