package org.acme.Interfaces;

import java.util.UUID;

import org.acme.Domains.Payment;

public interface IPaymentRepository {
    void addPayment();
    Payment getPayment(UUID paymentID);
    List<Payment> getPayments();
    void removePayment(UUID paymentID);
    
}
