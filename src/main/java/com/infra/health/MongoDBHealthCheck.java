package com.infra.health;

import com.codahale.metrics.health.HealthCheck;
import com.infra.config.MongoDBConfiguration;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBHealthCheck extends HealthCheck {
    private final MongoDBConfiguration mongoDB;

    public MongoDBHealthCheck(MongoDBConfiguration mongoDB) {
        this.mongoDB = mongoDB;
    }

    @Override
    protected Result check() throws Exception {
        try (MongoClient mongoClient = MongoClients.create(mongoDB.getMongoURI())) {
            MongoDatabase db = mongoClient.getDatabase(mongoDB.getDatabaseName());
            db.runCommand(new Document("ping", 1)); // connection was successful
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("Cannot connect to MongoDB database");
        }
    }
}
