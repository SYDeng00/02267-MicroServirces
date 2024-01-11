package org.acme.Domains;

import java.util.List;
import java.util.UUID;

public class Payment {
    private UUID paymentID;
    private UUID MerchantID;
    private List<String> tokens;
    private double amount;


    public Payment(){}
    
    public Payment(UUID paymentID, UUID merchantID, List<String> tokens, double amount) {
            this.paymentID = paymentID;
            MerchantID = merchantID;
            this.tokens = tokens;
            this.amount = amount;
    }

    
    public UUID getMerchantID() {
        return MerchantID;
    }
    public void setMerchantID(UUID merchantID) {
        MerchantID = merchantID;
    }
    public List<String> getTokens() {
        return tokens;
    }
    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
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
