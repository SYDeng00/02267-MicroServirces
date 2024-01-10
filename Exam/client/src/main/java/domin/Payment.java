package domin;

import java.math.BigDecimal;

public class Payment {
    String merchantDtuPayID;
    BigDecimal amount;
    String token;

    public Payment(){}


    public Payment(String merchantDtuPayID, BigDecimal amount, String token) {
        this.merchantDtuPayID = merchantDtuPayID;
        this.amount = amount;
        this.token = token;
    }

    public String getMerchantDtuPayID() {
        return merchantDtuPayID;
    }

    public void setMerchantDtuPayID(String merchantDtuPayID) {
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
