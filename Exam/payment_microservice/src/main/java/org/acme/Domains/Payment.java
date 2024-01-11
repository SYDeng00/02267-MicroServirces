package org.acme.Domains;

import java.util.List;
import java.util.UUID;

import org.acme.Models.Token;

import com.ibm.asyncutil.locks.AsyncLock.LockToken;

public class Payment {
    private UUID paymentID;
    private UUID MerchantID;
    private Token token;
    private double amount;


    public Payment(){}
    
    public Payment(UUID paymentID, UUID merchantID, Token token, double amount) {
            this.paymentID = paymentID;
            MerchantID = merchantID;
            this.token = token;
            this.amount = amount;
    }

    
    public UUID getMerchantID() {
        return MerchantID;
    }
    public void setMerchantID(UUID merchantID) {
        MerchantID = merchantID;
    }

    public Token getToken() {
        return token;
    }
    public void setToken(List<String> tokens) {
        this.token = token;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(UUID paymentID) {
        this.paymentID = paymentID;
    }

    
    
}
