package org.acme.Domains;

import java.math.BigDecimal;
import java.util.UUID;



public class Payment {
    private UUID paymentId;
    private UUID merchantId;
    private UUID customerId=null;
    private UUID messageId;
    private String token;
    private BigDecimal amount;

    public Payment(){}
    
    public Payment(UUID paymentId, UUID merchantId, String token, BigDecimal amount) {
        this.merchantId = merchantId;
        this.paymentId = paymentId;
        this.token = token;
        this.amount = amount;
    }
    public Payment(UUID paymentId, UUID messageId, UUID merchantId, String token, BigDecimal amount) {
        this.merchantId = merchantId;
        this.paymentId = paymentId;
        this.token = token;
        this.amount = amount;
        this.merchantId = messageId;
    }
    // public Payment(UUID paymentId, UUID merchantId, UUID customertID, String token, BigDecimal amount) {
    //         this.paymentId = paymentId;
    //         this.merchantId = merchantId;
    //         this.customerId = customertID;
    //         this.token = token;
    //         this.amount = amount;
    // }

    
    public UUID getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(UUID merchantID) {
        merchantId = merchantID;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getPaymentID() {
        return paymentId;
    }

    public void setPaymentID(UUID paymentID) {
        this.paymentId = paymentID;
    }

    public UUID getCustomerID() {
        return customerId;
    }

    public void setCustomerID(UUID customerID) {
        this.customerId = customerID;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
