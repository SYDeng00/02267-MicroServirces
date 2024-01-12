package org.acme.Domains;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.acme.Models.Token;

import com.ibm.asyncutil.locks.AsyncLock.LockToken;

public class Payment {
    private UUID PaymentID;
    private UUID MerchantID;
    private Token Token;
    private BigDecimal Amount;


    public Payment(){}
    
    public Payment(UUID merchantID, org.acme.Domains.Token token, BigDecimal amount) {
        MerchantID = merchantID;
        Token = token;
        Amount = amount;
    }

    public Payment(UUID paymentID, UUID merchantID, Token token, BigDecimal amount) {
            this.paymentID = paymentID;
            this.MerchantID = merchantID;
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
    public BigDecimal getAmount() {
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
