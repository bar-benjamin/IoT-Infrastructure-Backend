package com.infra;

import com.infra.representations.Address;
import com.infra.representations.Company;
import com.infra.representations.Contact;
import com.infra.representations.PaymentInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class IoTInfrastructureTest {
    // RULE is used to start and stop the application before and after tests
    @ClassRule
    public static final DropwizardAppRule<IoTInfrastructureConfiguration> RULE = new DropwizardAppRule<>(IoTInfrastructureApplication.class, "config.yml");

    private static WebResource companyResource;

    private static Company company;

    private static final String COMPANY_NAME = "BarGPT";

    private static final int COMPANY_ID = 1;


    @BeforeClass
    public static void setup() {
        companyResource = Client.create().resource("http://localhost:8080/GIOTI/companies");
        company = new Company(COMPANY_NAME,
                new Address(1, "Israel", "Tel-Aviv", "Gordon", 630105),
                new ArrayList<>(Arrays.asList(new Contact(1, "bar corporation", "BarGPT@gmail.com", "055-1112233", 1))),
                new ArrayList<>(Arrays.asList(new PaymentInfo(1, "5326458012345678", "03/25", "111"))));
        companyResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, company);
    }

    @Test
    public void testRegisterCompanyConflict() {
        ClientResponse response = companyResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, company);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetCompany() {
        ClientResponse response = companyResource.path(String.valueOf(COMPANY_ID))
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Company actualCompany = response.getEntity(Company.class);
        assertEquals(company, actualCompany);
    }
}
