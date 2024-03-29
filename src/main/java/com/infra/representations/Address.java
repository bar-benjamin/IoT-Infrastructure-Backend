package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Address {
    @JsonProperty("address_id")
    private int addressID;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("street")
    private String street;

    @JsonProperty("postal_code")
    private int postal_code;

    public Address() {
    }

    public Address(int addressID, String country, String city, String street, int postal_code) {
        this.addressID = addressID;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postal_code = postal_code;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(int postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address_id=" + addressID +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postal_code=" + postal_code +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressID == address.addressID && postal_code == address.postal_code && Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressID, country, city, street, postal_code);
    }
}
