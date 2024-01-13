package domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {
    UUID merchantDtuPayID;
    BigDecimal amount;
    String token;

    public Payment(){}


    public Payment(UUID merchantDtuPayID, String token, BigDecimal amount) {
        this.merchantDtuPayID = merchantDtuPayID;
        this.token = token;
        this.amount = amount;
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
