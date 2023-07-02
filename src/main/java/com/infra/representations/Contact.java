package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Contact {
    @JsonProperty("contact_id")
    private int contactID;

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("contact_email")
    @Pattern(regexp = "^[a-zA-Z][\\w\\-.]*@gmail\\.com$")
    private String contactEmail;

    @JsonProperty("contact_phone")
    @Pattern(regexp = "\\d{2,3}-?\\d{7}")
    private String contactPhone;

    @JsonProperty("address_id")
    private int addressID;

    public Contact() {
    }

    public Contact(int contactID, String contactName, String contactEmail, String contactPhone, int addressID) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.addressID = addressID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contact_id=" + contactID +
                ", contact_name='" + contactName + '\'' +
                ", contact_email='" + contactEmail + '\'' +
                ", contact_phone='" + contactPhone + '\'' +
                ", address_id=" + addressID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return contactID == contact.contactID && addressID == contact.addressID && Objects.equals(contactName, contact.contactName) && Objects.equals(contactEmail, contact.contactEmail) && Objects.equals(contactPhone, contact.contactPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactID, contactName, contactEmail, contactPhone, addressID);
    }
}
