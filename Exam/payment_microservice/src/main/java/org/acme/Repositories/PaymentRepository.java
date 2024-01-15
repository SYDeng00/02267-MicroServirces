package org.acme.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.acme.Domains.Payment;
import org.acme.Domains.Refund;
import org.acme.Interfaces.IPaymentRepository;
/**
 * Store all the pending payments and refund.
 * Add new payments and refund, remove them after the coresponding request finished
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class PaymentRepository implements IPaymentRepository{
    public static HashMap<UUID,Payment> payments = new HashMap<>();
    HashMap<UUID,Refund> refunds = new HashMap<>();
    private PaymentRepository() {
        // Private constructor to enforce singleton pattern
    }

    public static PaymentRepository getInstance() {
        return new PaymentRepository();
    }
    
    @Override
    public void addPayment(Payment payment) {
        payments.put(payment.getPaymentID(), payment);
    }

    @Override
    public Payment getPayment(UUID paymentID) {
        return payments.get(paymentID);
    }

    @Override
    public List<Payment> getPayments() {
        return new ArrayList<>(payments.values());
    }

    @Override
    public void removePayment(UUID paymentID) {
        payments.remove(paymentID);
    }

    @Override
    public void addRefund(Refund refund) {
        refunds.put(refund.getRefundId(),refund);
    }

    @Override
    public Refund getRefund(UUID refundID) {
        return refunds.get(refundID);
    }

    @Override
    public List<Refund> getRefunds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRefunds'");
    }

    @Override
    public void removeRefund(UUID refundID) {
        refunds.remove(refundID);
    }

   

    
    
}
