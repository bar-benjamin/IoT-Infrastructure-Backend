package com.infra.dao.mappers;

import com.infra.representations.Specification;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecificationMapper implements ResultSetMapper<Specification> {
    @Override
    public Specification map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        Specification specs = new Specification();

        specs.setSpecsID(r.getInt("specs_id"));
        specs.setProductPrice(r.getDouble("product_price"));
        specs.setProductCategory(r.getString("product_category"));
        specs.setProductWeight(r.getDouble("product_weight"));
        specs.setProductID(r.getInt("product_id"));

        return specs;
    }
}
