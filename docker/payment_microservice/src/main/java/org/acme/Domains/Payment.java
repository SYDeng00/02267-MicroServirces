
package org.acme.Domains;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class Payment {
    private UUID paymentId;
    private UUID merchantId;
    private UUID customerId=null;
    //private UUID messageId;
    private UUID token;
    private BigDecimal amount;

    public Payment(){}
  
    public Payment(UUID paymentId,  UUID merchantId, UUID token, BigDecimal amount) {
        this.merchantId = merchantId;
        this.paymentId = paymentId;
        this.token = token;
        this.amount = amount;
    }
    
    public UUID getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(UUID merchantID) {
        merchantId = merchantID;
    }

    public UUID getToken() {
        return token;
    }
    public void setToken(UUID token) {
        this.token = token;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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


}
