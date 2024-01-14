package org.acme.payment_facade.Resources;


import org.acme.Domains.Message;
import org.acme.Resoures.EventPublisher;
import org.acme.payment_facade.Domains.Payment;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class PaymentResources {
    @POST
    @Path("payments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPayment(Payment payment) {
        try {

            EventPublisher publisher = new EventPublisher();
            publisher.publishEvent(new Message(PaymentConfig.MERCHANT_ASK_PAYMENT, "PaymentBroker",new Object[]{payment.getMerchantDtuPayID(),payment.getToken(),payment.getAmount()}));
            return Response.status(201).entity("The payment was successful - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
