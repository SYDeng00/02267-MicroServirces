package org.acme.Interfaces;


import java.util.List;
import java.util.UUID;

import org.acme.Domains.Payment;
import org.acme.Domains.Refund;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */

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
