package account_Adapter;

import org.acme.payment_facade.EventPublisher;
import org.acme.payment_facade.PaymentConfig;

import domain.DTUPayAccount;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class AccountResources {
    @POST
    @Path("account")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPayment(DTUPayAccount customer) {
        try {

            EventPublisher publisher = new EventPublisher();
            publisher.publishEvent(new Message(PaymentConfig.MERCHANT_ASK_PAYMENT, new Object[]{payment.getMerchantDtuPayID(),payment.getToken(),payment.getAmount()}));
            return Response.status(201).entity("The payment was successful - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
