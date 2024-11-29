package com.vertx.custom.load.testing.repository;

import com.vertx.custom.load.testing.entity.LoadTest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadTestRepository extends MongoRepository<LoadTest, ObjectId> {

}
