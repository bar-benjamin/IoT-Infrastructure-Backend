package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Company {
    @JsonProperty("company_name")
    private String name;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("company_contacts")
    private List<Contact> contacts;

    @JsonProperty("payments_info")
    private List<PaymentInfo> paymentsInfo;

    public Company() {
    }

    public Company(String name, Address address, List<Contact> contacts, List<PaymentInfo> paymentsInfo) {
        this.name = name;
        this.address = address;
        this.contacts = contacts;
        this.paymentsInfo = paymentsInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<PaymentInfo> getPaymentsInfo() {
        return paymentsInfo;
    }

    public void setPaymentsInfo(List<PaymentInfo> paymentsInfo) {
        this.paymentsInfo = paymentsInfo;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", contacts=" + contacts +
                ", payments_info=" + paymentsInfo +
                '}';
    }
}
