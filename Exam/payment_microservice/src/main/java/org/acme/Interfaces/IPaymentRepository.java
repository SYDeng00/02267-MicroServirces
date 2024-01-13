package org.acme.interfaces;


import java.util.List;
import java.util.UUID;

import org.acme.domains.Payment;
import org.acme.domains.Refund;


public interface IPaymentRepository {
    void addPayment(Payment payment);
    Payment getPayment(UUID paymentID);
    List<Payment> getPayments();
    void removePayment(UUID paymentID);

    void addRefund(Refund refund);
    Refund getRefund(UUID refundID);
    List<Refund> getRefunds();
    void removeRefund(UUID refundID);

}
