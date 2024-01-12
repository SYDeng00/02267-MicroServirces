package org.acme.Domains;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.acme.Models.Token;


public class Payment {
    private UUID PaymentID;
    private UUID MerchantID;
    private Token Token;
    private BigDecimal Amount;


    public Payment(){}
    
    public Payment(UUID merchantID, Token token, BigDecimal amount) {
        MerchantID = merchantID;
        Token = token;
        Amount = amount;
    }

    public Payment(UUID paymentID, UUID merchantID, Token token, BigDecimal amount) {
            this.PaymentID = paymentID;
            this.MerchantID = merchantID;
            this.Token = token;
            this.Amount = amount;
    }

    
    public UUID getMerchantID() {
        return MerchantID;
    }
    public void setMerchantID(UUID merchantID) {
        MerchantID = merchantID;
    }

    public Token getToken() {
        return Token;
    }
    public void setToken(Token token) {
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

    
    
}
