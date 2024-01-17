package main.java.org.acme.report_facade.Domains;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Report {
    public enum Status {
        SUCCESS,
        FAILED,
        CANCELLED,
        REFUNDED
    }

    private UUID reportId;
    private UUID transactionId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private UUID customerId; // Optional based on privacy requirements
    private UUID merchantId;
    private Status status;

    // Constructor
    public Report(UUID reportId, UUID transactionId, BigDecimal amount, LocalDateTime dateTime, UUID customerId, UUID merchantId, Status status) {
        this.reportId = reportId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.status = status;
    }

    // Getters and Setters
    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
