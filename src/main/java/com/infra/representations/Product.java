package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Product {
    @JsonProperty("product_id")
    private int productID;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_specification")
    private Specification productSpecification;

    public Product() {
    }

    public Product(int productID, String productName, Specification productSpecification) {
        this.productID = productID;
        this.productName = productName;
        this.productSpecification = productSpecification;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Specification getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(Specification productSpecification) {
        this.productSpecification = productSpecification;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + productID +
                ", product_name='" + productName + '\'' +
                ", product_specification=" + productSpecification +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID == product.productID && Objects.equals(productName, product.productName) && Objects.equals(productSpecification, product.productSpecification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, productName, productSpecification);
    }
}
