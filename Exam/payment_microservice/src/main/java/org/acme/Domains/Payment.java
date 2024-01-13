package org.acme.Domains;

import java.math.BigDecimal;
import java.util.UUID;



public class Payment {
    private UUID PaymentID;
    private UUID MerchantID;
    private UUID CustomerID;
    

    private String Token;
    private BigDecimal Amount;


    public Payment(){}
    
    public Payment(UUID merchantID, UUID customertID,String token, BigDecimal amount) {
        this.MerchantID = merchantID;
        this.CustomerID = customertID;
        this.Token = token;
        this.Amount = amount;
    }

    public Payment(UUID paymentID, UUID merchantID, UUID customertID, String token, BigDecimal amount) {
            this.PaymentID = paymentID;
            this.MerchantID = merchantID;
            this.CustomerID = customertID;
            this.Token = token;
            this.Amount = amount;
    }

    
    public UUID getMerchantID() {
        return MerchantID;
    }
    public void setMerchantID(UUID merchantID) {
        MerchantID = merchantID;
    }

    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        this.Token = token;
    }
    public BigDecimal getAmount() {
        return Amount;
    }
    public void setAmount(BigDecimal amount) {
        this.Amount = amount;
    }

    public UUID getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(UUID paymentID) {
        this.PaymentID = paymentID;
    }

    public UUID getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(UUID customerID) {
        CustomerID = customerID;
    }
}
