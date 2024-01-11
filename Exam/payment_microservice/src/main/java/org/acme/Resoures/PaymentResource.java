package org.acme.Resoures;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class PaymentResource {
    public static void main(String[] args) throws Exception {
        PaymentBroker paymentBroker = new PaymentBroker();
        Quarkus.run();
    }
}
