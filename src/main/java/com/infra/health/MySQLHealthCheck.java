package com.infra.health;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.db.DataSourceFactory;

import java.sql.Connection;

public class MySQLHealthCheck extends HealthCheck {
    private final DataSourceFactory database;

    public MySQLHealthCheck(DataSourceFactory database) {
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
        try (Connection connection = database.build(null, "mysql").getConnection()) { // connection was successful
            return Result.healthy();
        } catch (Exception e){
            return Result.unhealthy("Cannot connect to MySQL database");
        }
    }
}
