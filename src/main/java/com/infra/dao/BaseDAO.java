package com.infra.dao;

import com.infra.representations.Address;
import com.infra.representations.PaymentInfo;
import org.skife.jdbi.v2.Handle;

import java.util.List;

public abstract class BaseDAO {
    protected final Handle h;

    public BaseDAO(Handle h) {
        this.h = h;
    }

    public void useDatabase(String databaseName) {
        h.execute("USE " + databaseName);
    }

    public boolean isCompanyExist(String companyName) {
        useDatabase("CompaniesManager");
        return h.createQuery("SELECT company_name FROM Companies WHERE company_name = :company_name")
                .bind("company_name", companyName)
                .mapTo(String.class)
                .first() != null;
    }

    public boolean isProductExist(String productName) {
        return h.createQuery("SELECT product_name FROM Products WHERE product_name = :product_name")
                .bind("product_name", productName)
                .mapTo(String.class)
                .first() != null;
    }

    public boolean isIoTExist(int deviceSerialNumber) {
        return h.createQuery("SELECT device_serial_number FROM IOTDevices WHERE device_serial_number = :device_serial_number")
                .bind("device_serial_number", deviceSerialNumber)
                .mapTo(Integer.class)
                .first() != null;
    }

    public String getProductNameByID(int productID) {
        return h.createQuery("SELECT product_name FROM Products WHERE product_id = :product_id")
                .bind("product_id", productID)
                .mapTo(String.class)
                .first();
    }

    protected int getLastInsertedIDFromTable(String tableName) {
        return h.createQuery("SELECT LAST_INSERT_ID() FROM " + tableName)
                .mapTo(Integer.class)
                .first();
    }

    protected void saveAddress(Address address) {
        h.createStatement("INSERT INTO Addresses (country, city, street, postal_code) VALUES (:country, :city, :street, :postal_code)")
                .bind("country", address.getCountry())
                .bind("city", address.getCity())
                .bind("street", address.getStreet())
                .bind("postal_code", address.getPostalCode())
                .execute();
    }

    protected void savePaymentsInfo(List<PaymentInfo> payments) {
        for (PaymentInfo payment : payments) {
            h.createStatement("INSERT INTO PaymentInfo (credit_card, expiration_date, cvv, is_company) VALUES (:credit_card, :expiration_date, :cvv, :is_company)")
                    .bind("credit_card", payment.getCreditCard())
                    .bind("expiration_date", payment.getExpirationDate())
                    .bind("cvv", payment.getCVV())
                    .bind("is_company", true)
                    .execute();
        }
    }
}
