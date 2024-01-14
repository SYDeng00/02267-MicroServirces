package main.java.org.acme.payment_facade.Resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.payment_facade.Domains.Payment;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
@Path("/")
public class PaymentFacadeResources {
    @POST
    @Path("payments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPayment(Payment payment) {
        try {
            PaymentFacadeBroker paymentBroker = new PaymentFacadeBroker();
            paymentBroker.received();
            paymentBroker.sendPaymentRequestToPaymentService(payment);
            return Response.status(201).entity("The payment was successful - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}