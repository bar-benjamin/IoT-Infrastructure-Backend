package com.infra.dao;


import com.infra.dao.mappers.SpecificationMapper;
import com.infra.representations.Product;
import com.infra.representations.Specification;
import org.skife.jdbi.v2.Handle;

import java.util.List;

public class ProductDAO extends BaseDAO {
    public ProductDAO(Handle h) {
        super(h);
    }

    public void registerProduct(Product product) {
        saveProduct(product.getProductName());
        saveProductSpecification(product.getProductSpecification(), getLastInsertedIDFromTable("Products"));
    }

    // helper methods
    private void saveProduct(String productName) {
        h.createStatement("INSERT INTO Products (product_name) VALUES (:product_name)")
                .bind("product_name", productName)
                .execute();
    }

    private void saveProductSpecification(Specification specs, int productID) {
        h.createStatement("INSERT INTO Specification (product_price, product_category, product_weight, product_id) VALUES (:product_price, :product_category, :product_weight, :product_id)")
                .bind("product_price", specs.getProductPrice())
                .bind("product_category", specs.getProductCategory())
                .bind("product_weight", specs.getProductWeight())
                .bind("product_id", productID)
                .execute();
    }

    public Specification getProductSpecification(int productID) {
        return h.createQuery("SELECT * FROM Specification WHERE product_id = :product_id")
                .bind("product_id", productID)
                .map(new SpecificationMapper())
                .first();
    }

    public List<Integer> getProductsIDs() {
        return h.createQuery("SELECT product_id FROM Products")
                .mapTo(Integer.class)
                .list();
    }
}
