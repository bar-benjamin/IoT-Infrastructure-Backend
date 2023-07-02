package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class Specification {
    @JsonProperty("specs_id")
    private int specsID;

    @Positive
    @JsonProperty("product_price")
    private double productPrice;

    @JsonProperty("product_category")
    private String productCategory;

    @Positive
    @JsonProperty("product_weight")
    private double productWeight;

    @JsonProperty("product_id")
    private int productID;

    public Specification() {}

    public Specification(int specsID, double productPrice, String productCategory, double productWeight, int productID) {
        this.specsID = specsID;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productWeight = productWeight;
        this.productID = productID;
    }

    public int getSpecsID() {
        return specsID;
    }

    public void setSpecsID(int specsID) {
        this.specsID = specsID;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "specs_id=" + specsID +
                ", product_price=" + productPrice +
                ", product_category='" + productCategory + '\'' +
                ", product_weight=" + productWeight +
                ", product_id=" + productID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specification that = (Specification) o;
        return specsID == that.specsID && Double.compare(that.productPrice, productPrice) == 0 && Double.compare(that.productWeight, productWeight) == 0 && productID == that.productID && Objects.equals(productCategory, that.productCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specsID, productPrice, productCategory, productWeight, productID);
    }
}
