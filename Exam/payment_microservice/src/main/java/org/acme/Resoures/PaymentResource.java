package org.acme.Resoures;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
@QuarkusMain
@Path("/")
public class PaymentResource {
    static PaymentBroker paymentBroker;
    public PaymentResource() {
        try {
            paymentBroker = new PaymentBroker();
            // ...
        } catch (Exception e) {
            // Handle the exception or log it
            e.printStackTrace();
        }
        
    }
    @Path("payments")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void clientReceive(Payment payment){
           
    }
    public static void main(String[] args){
        
        //PaymentBroker paymentBroker = new PaymentBroker();
        PaymentResource paymentResource = new PaymentResource();
        System.out.println("Payment service lauched, start Broker");
        try {
            paymentBroker.received();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Quarkus.run();
    }
}
