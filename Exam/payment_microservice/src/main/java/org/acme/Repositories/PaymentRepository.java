package org.acme.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.acme.interfaces.IPaymentRepository;
import org.acme.domains.Payment;
import org.acme.domains.Refund;

public class PaymentRepository implements IPaymentRepository{
    HashMap<UUID,Payment> payments = new HashMap<>();
    HashMap<UUID,Refund> refunds = new HashMap<>();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPayments'");
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
