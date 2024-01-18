package Domains;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {
	UUID merchantDtuPayID;
    BigDecimal amount;
    UUID token;

    public Payment(){}


    public Payment(UUID merchantDtuPayID2, UUID token, BigDecimal amount) {
        this.merchantDtuPayID = merchantDtuPayID2;
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

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
