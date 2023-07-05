package com.infra;

import com.infra.filters.CORSFilter;
import com.infra.health.MongoDBHealthCheck;
import com.infra.health.MySQLHealthCheck;
import com.infra.resources.CompanyResource;
import com.infra.resources.IoTResource;
import com.infra.resources.IoTUpdateResource;
import com.infra.resources.ProductResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class IoTInfrastructureApplication extends Application<IoTInfrastructureConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IoTInfrastructureApplication.class);

    public static void main(String[] args) throws Exception {
        new IoTInfrastructureApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<IoTInfrastructureConfiguration> bootstrap) {
    }

    @Override
    public void run(IoTInfrastructureConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Method MultiProtocolServerConfiguration#run() called");

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        environment.jersey().register(new CompanyResource(jdbi));
        environment.jersey().register(new ProductResource(jdbi));
        environment.jersey().register(new IoTResource(jdbi));
        environment.jersey().register(new IoTUpdateResource(configuration.getMongoDB()));

        environment.healthChecks().register("mysql", new MySQLHealthCheck(configuration.getDataSourceFactory()));
        environment.healthChecks().register("mongodb", new MongoDBHealthCheck(configuration.getMongoDB()));

        // Register CORS filter
        FilterRegistration.Dynamic corsFilter = environment.servlets()
                .addFilter("CORSFilter", new CORSFilter());
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
