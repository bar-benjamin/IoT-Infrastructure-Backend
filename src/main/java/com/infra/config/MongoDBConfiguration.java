package com.infra.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MongoDBConfiguration {
    @JsonProperty
    private String mongoURI;

    @JsonProperty
    private String databaseName;

    @JsonProperty
    private String collectionName;

    public String getMongoURI() {
        return mongoURI;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getCollectionName() {
        return collectionName;
    }
}
