package com.infra.resources;

import com.infra.dao.CompanyDAO;
import com.infra.representations.Address;
import com.infra.representations.Company;
import com.infra.representations.Contact;
import com.infra.representations.PaymentInfo;
import org.skife.jdbi.v2.DBI;

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
        if (dao.isCompanyExist(company.getName())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Error: company already exists in database")
                    .build();
        }

        dao.registerCompany(company);
        return Response.status(Response.Status.CREATED).entity("Success: company was registered in database").build();
    }

    @GET
    @Path("/{company_id}")
    public Response getCompany(@PathParam("company_id") int companyID) {
        String companyName = dao.getCompanyNameByID(companyID);

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

    @DELETE
    @Path("/{company_name}")
    public Response deleteCompany(@PathParam("company_name") String companyName) {
        if (!dao.isCompanyExist(companyName)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error: no such company in database")
                    .build();
        }

        dao.deleteCompany(companyName);
        return Response.ok("Success: company was deleted from database").build();
    }

    // helper methods
    private Company getCompanyObject(String companyName) {
        dao.useDatabase(companyName);
        Address address = dao.getAddress();
        List<Contact> contacts = dao.getContacts();
        List<PaymentInfo> paymentsInfo = dao.getPaymentsInfo();
        return new Company(companyName, address, contacts, paymentsInfo);
    }
}
