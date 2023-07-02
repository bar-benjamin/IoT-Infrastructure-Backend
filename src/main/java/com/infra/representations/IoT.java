package com.infra.representations;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class IoT {
    @JsonProperty("device_serial_number")
    private int deviceSerialNumber;

    @JsonProperty("owner_name")
    private String ownerName;

    @JsonProperty("owner_email")
    private String ownerEmail;

    @JsonProperty("owner_phone")
    private String ownerPhone;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("payments_info")
    private List<PaymentInfo> paymentsInfo;

    public IoT() {
    }

    public IoT(int deviceSerialNumber, String ownerName, String ownerEmail,
               String ownerPhone, Address address, List<PaymentInfo> paymentsInfo) {
        this.deviceSerialNumber = deviceSerialNumber;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPhone = ownerPhone;
        this.address = address;
        this.paymentsInfo = paymentsInfo;
    }

    public int getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(int deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<PaymentInfo> getPaymentsInfo() {
        return paymentsInfo;
    }

    public void setPaymentsInfo(List<PaymentInfo> paymentsInfo) {
        this.paymentsInfo = paymentsInfo;
    }

    @Override
    public String toString() {
        return "IoT{" +
                "device_serial_number=" + deviceSerialNumber +
                ", owner_name='" + ownerName + '\'' +
                ", owner_email='" + ownerEmail + '\'' +
                ", owner_phone='" + ownerPhone + '\'' +
                ", address=" + address +
                ", payments_info=" + paymentsInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoT ioT = (IoT) o;
        return deviceSerialNumber == ioT.deviceSerialNumber && Objects.equals(ownerName, ioT.ownerName) && Objects.equals(ownerEmail, ioT.ownerEmail) && Objects.equals(ownerPhone, ioT.ownerPhone) && Objects.equals(address, ioT.address) && Objects.equals(paymentsInfo, ioT.paymentsInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceSerialNumber, ownerName, ownerEmail, ownerPhone, address, paymentsInfo);
    }
}
