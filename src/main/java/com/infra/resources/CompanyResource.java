package com.infra.resources;

import com.infra.dao.CompanyDAO;
import com.infra.representations.Address;
import com.infra.representations.Company;
import com.infra.representations.Contact;
import com.infra.representations.PaymentInfo;
import com.rabbitmq.client.Channel;
import org.skife.jdbi.v2.DBI;

import com.rabbitmq.client.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/GIOTI/companies")
public class CompanyResource {
    private final CompanyDAO dao;

    public CompanyResource(DBI jdbi) {
        dao = new CompanyDAO(jdbi.open());

        dao.createCompaniesManagerIfNotExist();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCompany(Company company) {
        String companyName = company.getName();
        if (dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Error: company already exists in database")
                    .build();
        }

        publishCompanyNameToMQ(companyName);

        dao.registerCompany(company);
        return Response.status(Response.Status.CREATED).entity("Success: company was registered in database").build();
    }

    @GET
    @Path("/{company_name}")
    public Response getCompany(@PathParam("company_name") String companyName) {
        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        Company company = getCompanyObject(companyName);
        return Response.ok(company).build();
    }

    @GET
    public Response getCompanies() {
        List<Company> companies = new ArrayList<>();

        List<String> companyNames = dao.getCompanyNames();
        if (companyNames.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no companies in database")
                    .build();
        }

        for (String companyName: companyNames) {
            Company company = getCompanyObject(companyName);
            companies.add(company);
        }

        return Response.ok(companies).build();
    }

    // helper methods
    private Company getCompanyObject(String companyName) {
        dao.useDatabase(companyName);
        Address address = dao.getAddress();
        List<Contact> contacts = dao.getContacts();
        List<PaymentInfo> paymentsInfo = dao.getPaymentsInfo();
        return new Company(companyName, address, contacts, paymentsInfo);
    }

    private void publishCompanyNameToMQ(String companyName) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String exchangeName = "company_exchange";
            String routingKey = "new_company";

            channel.basicPublish(exchangeName, routingKey, null, companyName.getBytes());
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
