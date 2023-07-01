package com.infra.representations;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

public class PaymentInfo {
    @JsonProperty("payment_id")
    private int paymentID;

    @JsonProperty("credit_card")
    @Pattern(regexp = "^(5[1-5][0-9]{14}|4[0-9]{12}(?:[0-9]{3})?)$")
    private String creditCard;

    @JsonProperty("expiration_date")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-2][1-9]|3[0-1])$")
    private String expirationDate;

    @JsonProperty("cvv")
    @Pattern(regexp = "^[0-9]{3}$")
    private String cvv;

    public PaymentInfo() {
    }

    public PaymentInfo(int paymentID, String creditCard, String expirationDate, String cvv) {
        this.paymentID = paymentID;
        this.creditCard = creditCard;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCVV() {
        return cvv;
    }

    public void setCVV(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "payment_id=" + paymentID +
                ", credit_card='" + creditCard + '\'' +
                ", expiration_date='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
