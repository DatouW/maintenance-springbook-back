package com.group8.code;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
public class MongoDBConnectionTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoConnection() {
        mongoTemplate.getDb().listCollectionNames().forEach(System.out::println);
    }
}
