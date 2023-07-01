package com.infra;

import com.infra.config.MongoDBConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.*;

public class IoTInfrastructureConfiguration extends Configuration {
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    private MongoDBConfiguration mongodb = new MongoDBConfiguration();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public MongoDBConfiguration getMongoDB() {
        return mongodb;
    }
}
