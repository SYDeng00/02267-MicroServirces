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
    PaymentFacadeBroker paymentBroker= new PaymentFacadeBroker();;
    @POST
    @Path("payments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPayment(Payment payment) {
        try {
            paymentBroker.received();
            String status = paymentBroker.sendPaymentRequestToPaymentService(payment);
            if (status.equals("200")){
                return Response.status(200).entity("payment is successful").build();
            }
            return Response.status(400).entity("payment is failed").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
