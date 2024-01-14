package org.acme.Domains;

import java.math.BigDecimal;
import java.util.UUID;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class Refund {
    UUID refundId;
    UUID merchUuid;
    UUID paymentId;
    BigDecimal amount;

    

    public Refund(UUID refundId, UUID paymentId,UUID merchaUuid, BigDecimal amount) {
        this.refundId = refundId;
        this.paymentId = paymentId;
        this.merchUuid = merchaUuid;
        this.amount = amount;
    }

    public Refund() {
    }


    public UUID getRefundId() {
        return refundId;
    }

    public void setRefundId(UUID refundId) {
        this.refundId = refundId;
    }
    
    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public UUID getMerchUuid() {
        return merchUuid;
    }

    public void setMerchUuid(UUID merchUuid) {
        this.merchUuid = merchUuid;
    }
}
