package com.infra.dao.mappers;

import com.infra.representations.Contact;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactMapper implements ResultSetMapper<List<Contact>> {
    @Override
    public List<Contact> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        List<Contact> contacts = new ArrayList<>();

        do {
            Contact contact = new Contact();

            contact.setContactID(r.getInt("contact_id"));
            contact.setContactName(r.getString("contact_name"));
            contact.setContactEmail(r.getString("contact_email"));
            contact.setContactPhone(r.getString("contact_phone"));
            contact.setAddressID(r.getInt("address_id"));

            contacts.add(contact);
        } while(r.next());

        return contacts;
    }
}