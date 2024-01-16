package main.java.org.acme.payment_facade.Domains;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class Payment {
    UUID merchantDtuPayID;
    BigDecimal amount;
    String token;
    public Payment(){}
    public Payment(UUID merchantDtuPayID, String token, int amount) {
        this.merchantDtuPayID = merchantDtuPayID;
        this.token = token;
        this.amount = BigDecimal.valueOf(amount);
    }

    public UUID getMerchantDtuPayID() {
        return merchantDtuPayID;
    }

    public void setMerchantDtuPayID(UUID merchantDtuPayID) {
        this.merchantDtuPayID = merchantDtuPayID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
