package com.infra.dao;

import com.infra.dao.mappers.AddressMapper;
import com.infra.dao.mappers.PaymentInfoMapper;
import com.infra.representations.Address;
import com.infra.representations.IoT;
import com.infra.representations.PaymentInfo;
import org.skife.jdbi.v2.Handle;

import java.util.List;

public class IoTDAO extends BaseDAO {
    public IoTDAO(Handle h) {
        super(h);
    }

    public void registerIOT(IoT iot, int productID) {
        saveOwnerDetails(iot.getOwnerName(), iot.getOwnerEmail(), iot.getOwnerPhone(),
                         iot.getAddress(), iot.getPaymentsInfo());

        int ownerID = getLastInsertedIDFromTable("IOTOwners");
        saveIoTDevice(productID, ownerID);
    }

    // helper methods
    private void saveOwnerDetails(String ownerName, String ownerEmail, String ownerPhone,
                                  Address address, List<PaymentInfo> paymentsInfo) {
        saveAddress(address);
        int addressID = getLastInsertedIDFromTable("Addresses");

        savePaymentsInfo(paymentsInfo);
        int paymentID = getLastInsertedIDFromTable("PaymentInfo");

        h.createStatement("INSERT INTO IOTOwners (owner_name, owner_email, owner_phone, address_id, payment_id) VALUES(:owner_name, :owner_email, :owner_phone, :address_id, :payment_id)")
                .bind("owner_name", ownerName)
                .bind("owner_email", ownerEmail)
                .bind("owner_phone", ownerPhone)
                .bind("address_id", addressID)
                .bind("payment_id", paymentID)
                .execute();
    }

    private void saveIoTDevice(int productID, int ownerID) {
        h.createStatement("INSERT INTO IOTDevices (product_id, owner_id) VALUES(:product_id, :owner_id)")
                .bind("product_id", productID)
                .bind("owner_id", ownerID)
                .execute();
    }

    public String getOwnerNameByID(int deviceSerialNumber) {
        return h.createQuery("SELECT owner_name FROM IOTOwners WHERE owner_id = (SELECT owner_id FROM IOTDevices WHERE device_serial_number = :device_serial_number)")
                .bind("device_serial_number", deviceSerialNumber)
                .mapTo(String.class)
                .first();
    }

    public String getOwnerEmailByID(int deviceSerialNumber) {
        return h.createQuery("SELECT owner_email FROM IOTOwners WHERE owner_id = (SELECT owner_id FROM IOTDevices WHERE device_serial_number = :device_serial_number)")
                .bind("device_serial_number", deviceSerialNumber)
                .mapTo(String.class)
                .first();
    }

    public String getOwnerPhoneByID(int deviceSerialNumber) {
        return h.createQuery("SELECT owner_phone FROM IOTOwners WHERE owner_id = (SELECT owner_id FROM IOTDevices WHERE device_serial_number = :device_serial_number)")
                .bind("device_serial_number", deviceSerialNumber)
                .mapTo(String.class)
                .first();
    }

    public Address getOwnerAddressByID(int deviceSerialNumber) {
        return h.createQuery("SELECT * FROM Addresses WHERE address_id = (SELECT address_id FROM IOTOwners WHERE owner_id = (SELECT owner_id FROM IOTDevices WHERE device_serial_number = :device_serial_number))")
                .bind("device_serial_number", deviceSerialNumber)
                .map(new AddressMapper())
                .first();
    }

    public List<PaymentInfo> getPaymentsByID(int deviceSerialNumber) {
        return h.createQuery("SELECT * FROM PaymentInfo WHERE payment_id = (SELECT payment_id FROM IOTOwners WHERE owner_id = (SELECT owner_id FROM IOTDevices WHERE device_serial_number = :device_serial_number))")
                .bind("device_serial_number", deviceSerialNumber)
                .map(new PaymentInfoMapper())
                .first();
    }

    public List<String> getIoTs(int productID) {
        return h.createQuery("SELECT device_serial_number FROM IOTDevices WHERE product_id = :product_id")
                .bind("product_id", productID)
                .mapTo(String.class)
                .list();
    }

    public int getProductIDByName(String productName) {
        return h.createQuery("SELECT product_id FROM Products WHERE product_name = :product_name")
                .bind("product_name", productName)
                .mapTo(Integer.class)
                .first();
    }
}
