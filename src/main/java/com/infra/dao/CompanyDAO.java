package com.infra.dao;

import com.infra.dao.mappers.AddressMapper;
import com.infra.dao.mappers.ContactMapper;
import com.infra.dao.mappers.PaymentInfoMapper;
import com.infra.representations.Address;
import com.infra.representations.Company;
import com.infra.representations.Contact;
import com.infra.representations.PaymentInfo;
import org.skife.jdbi.v2.Handle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CompanyDAO extends BaseDAO {
    public CompanyDAO(Handle h) {
        super(h);
    }

    public void registerCompany(Company company) {
        saveCompanyInManager(company.getName());

        createCompanyDatabase(company.getName());

        useDatabase(company.getName());
        saveAddress(company.getAddress());
        saveContacts(company.getContacts(), getLastInsertedIDFromTable("Addresses"));
        savePaymentsInfo(company.getPaymentsInfo());
    }

    public void createCompaniesManagerIfNotExist() {
        h.createBatch()
                .add("CREATE DATABASE IF NOT EXISTS CompaniesManager")
                .add("USE CompaniesManager")
                .add("CREATE TABLE IF NOT EXISTS Companies (" +
                        "company_id INT AUTO_INCREMENT, " +
                        "company_name VARCHAR(255), " +
                        "PRIMARY KEY (company_id))")
                .execute();
    }

    public List<String> getCompanyNames() {
        useDatabase("CompaniesManager");
        return h.createQuery("SELECT company_name FROM Companies")
                .mapTo(String.class)
                .list();
    }

    public Address getAddress() {
        return h.createQuery("SELECT * FROM Addresses")
                .map(new AddressMapper())
                .first();
    }

    public List<Contact> getContacts() {
        return h.createQuery("SELECT * FROM CompanyContacts")
                .map(new ContactMapper())
                .first();
    }

    public List<PaymentInfo> getPaymentsInfo() {
        return h.createQuery("SELECT * FROM PaymentInfo")
                .map(new PaymentInfoMapper())
                .first();
    }

    public void deleteCompany(String companyName) {
        useDatabase("CompaniesManager");
        h.createStatement("DELETE FROM Companies WHERE company_name = :company_name")
                .bind("company_name", companyName)
                .execute();
        h.createStatement("DROP DATABASE :company_name")
                .bind("company_name", companyName)
                .execute();
    }

    // helper methods
    private void createCompanyDatabase(String companyName) {
        String sqlScript;
        try {
            sqlScript = String.join("\n",
                    Files.readAllLines(
                            Paths.get("company.sql")))
                    .replace("{{DB_NAME_PLACEHOLDER}}", companyName);
        } catch (IOException ex) {
            throw new RuntimeException();
        }

        h.createScript(sqlScript)
                .execute();
    }

    private void saveCompanyInManager(String companyName) {
        useDatabase("CompaniesManager");
        h.createStatement("INSERT INTO Companies (company_name) VALUES (:company_name)")
                .bind("company_name", companyName)
                .execute();
    }

    private void saveContacts(List<Contact> contacts, int address_id) {
        for (Contact contact: contacts) {
            h.createStatement("INSERT INTO CompanyContacts (contact_name, contact_email, contact_phone, address_id) VALUES (:contact_name, :contact_email, :contact_phone, :address_id)")
                    .bind("contact_name", contact.getContactName())
                    .bind("contact_email", contact.getContactEmail())
                    .bind("contact_phone", contact.getContactPhone())
                    .bind("address_id", address_id)
                    .execute();
        }
    }
}