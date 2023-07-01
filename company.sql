CREATE DATABASE {{DB_NAME_PLACEHOLDER}};

USE {{DB_NAME_PLACEHOLDER}};

CREATE TABLE Addresses
(
    address_id  INT AUTO_INCREMENT,
    country     VARCHAR(255),
    city        VARCHAR(255),
    street      VARCHAR(255),
    postal_code INT,

    PRIMARY KEY (address_id)
);

CREATE TABLE CompanyContacts
(
    contact_id    INT AUTO_INCREMENT,
    contact_name  VARCHAR(255),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(255),
    address_id    INT,

    PRIMARY KEY (contact_id),
    FOREIGN KEY (address_id) REFERENCES Addresses (address_id)
);

CREATE TABLE PaymentInfo
(
    payment_id      INT AUTO_INCREMENT,
    credit_card     VARCHAR(16),
    expiration_date VARCHAR(5),
    cvv             INT,
    is_company      BOOLEAN,

    PRIMARY KEY (payment_id)
);

CREATE TABLE Products
(
    product_id   INT AUTO_INCREMENT,
    product_name VARCHAR(255),

    PRIMARY KEY (product_id),
    UNIQUE (product_name)
);

CREATE TABLE Specification
(
    specs_id         INT AUTO_INCREMENT,
    product_price    DOUBLE,
    product_category VARCHAR(255),
    product_weight   DOUBLE,
    product_id       INT,

    PRIMARY KEY (specs_id),
    FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

CREATE TABLE IOTOwners
(
    owner_id    INT AUTO_INCREMENT,
    owner_name  VARCHAR(255),
    owner_email VARCHAR(255),
    owner_phone VARCHAR(255),
    address_id  INT,
    payment_id  INT,

    PRIMARY KEY (owner_id),
    FOREIGN KEY (address_id) REFERENCES Addresses (address_id),
    FOREIGN KEY (payment_id) REFERENCES PaymentInfo (payment_id)
);

CREATE TABLE IOTDevices
(
    device_serial_number INT AUTO_INCREMENT,
    product_id           INT,
    owner_id             INT,

    PRIMARY KEY (device_serial_number),
    FOREIGN KEY (product_id) REFERENCES Products (product_id),
    FOREIGN KEY (owner_id) REFERENCES IOTOwners (owner_id)
);

CREATE TABLE IOTData
(
    iot_data_id          INT AUTO_INCREMENT,
    iot_data             VARCHAR(255),
    iot_timestamp        TIMESTAMP,
    device_serial_number INT,

    PRIMARY KEY (iot_data_id),
    FOREIGN KEY (device_serial_number) REFERENCES IOTDevices (device_serial_number)
);