package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
