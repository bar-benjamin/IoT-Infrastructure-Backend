package com.infra;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.infra.representations.*;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

// TODO: Note - App must be running before running tests

public class IoTInfrastructureTest {
    // RULE is used to start and stop the application during the test
    @ClassRule
    public static final DropwizardAppRule<IoTInfrastructureConfiguration> RULE = new DropwizardAppRule<>(IoTInfrastructureApplication.class, "config.yml");

    private static WebResource companyResource;

    private static Company company;

    private static final String COMPANY_NAME = "BarGPT";

    @BeforeAll
    public static void setup() {
        companyResource = Client.create().resource("http://localhost:8080/GIOTI/companies");
        company = new Company(COMPANY_NAME,
                new Address(1, "Israel", "Tel-Aviv", "Gordon", 630105),
                new ArrayList<>(Arrays.asList(new Contact(1, "bar corporation", "BarGPT@gmail.com", "055-1112233", 1))),
                new ArrayList<>(Arrays.asList(new PaymentInfo(1, "5326458012345678", "03/25", "111"))));
    }

    @Test
    public void testRegisterCompanyConflict() {
        ClientResponse existResponse = companyResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, company);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), existResponse.getStatus());
    }

    @Test
    public void testGetCompany() {
        int companyId = 1;
        ClientResponse response = companyResource.path(String.valueOf(companyId))
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Company actualCompany = response.getEntity(Company.class);
        assertEquals(company, actualCompany);
    }
}