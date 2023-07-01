package com.infra.dao.mappers;

import com.infra.representations.Address;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements ResultSetMapper<Address> {

    @Override
    public Address map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Address address = new Address();

        address.setAddressID(r.getInt("address_id"));
        address.setCountry(r.getString("country"));
        address.setCity(r.getString("city"));
        address.setStreet(r.getString("street"));
        address.setPostalCode(r.getInt("postal_code"));

        return address;
    }
}
