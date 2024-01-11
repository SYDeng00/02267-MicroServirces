package org.acme.Resoures;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.acme.Domains.Payment;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Interfaces.IPaymentRepository;

public class PaymentRepository implements IPaymentRepository{
    HashMap<UUID,Payment> payments = new HashMap<>();
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
    }

    
    
}
