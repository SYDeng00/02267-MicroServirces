package main.java.org.acme.report_facade.Domains;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class Report {
    private UUID reportId;
    private UUID payOrRefundUUID;
    private UUID token;
    private BigDecimal amount;
    private UUID customerId; // Optional based on privacy requirements
    private UUID merchantId;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    private LocalDateTime dateTime;

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }
    public Report(){}
    public Report(UUID reportId, UUID token, BigDecimal amount, UUID payOrRefundUUID, String payType , UUID merchantId) {
        this.reportId = reportId;
        this.payOrRefundUUID = payOrRefundUUID;
        this.token = token;
        this.amount = amount;
        this.merchantId = merchantId;
        this.payType = payType;
    }

    private String payType;
    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Report(UUID payOrRefundUUID, UUID token, BigDecimal amount, UUID customerId, UUID merchantId, String payType) {
        this.payOrRefundUUID = payOrRefundUUID;
        this.token = token;
        this.amount = amount;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.payType = payType;
    }

    // Getters and Setters
    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public UUID getPayOrRefundUUID() {
        return payOrRefundUUID;
    }

    public void setPayOrRefundUUID(UUID payOrRefundUUID) {
        this.payOrRefundUUID = payOrRefundUUID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;

    }
}
