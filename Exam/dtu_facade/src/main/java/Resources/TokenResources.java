package Resources;

import Domains.DTUPayAccount;
import Domains.Token;
import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TokenResources implements IEventSubscriber{

    EventPublisher publisher = new EventPublisher();
    EventSubscriber subscriber = new EventSubscriber( this);



    String receivedId;

    private CompletableFuture<String> idFuture;


    public TokenResources() {

        try {
            subscriber.subscribeEvent("TokenResources");
            idFuture = new CompletableFuture<>();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @POST
    @Path("accounts")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAccount(DTUPayAccount account) {
        try {

            publisher.publishEvent(new Message(AccountConfig.REGISTER, "AccountBroker", new Object[] { account }));

            idFuture = new CompletableFuture<>();

            String id = idFuture.get(10, TimeUnit.SECONDS); //wait for 10 seconds

            return Response.status(201).entity("The Account was successful - ID: " + id).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }

    @Path("/tokens/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTokenSet(Token token)  {
        try
        {
        publisher.publishEvent(new Message(TokenConfig.RETURN_TOKEN, "TokenBroker", new Object[] { token }));

        idFuture = new CompletableFuture<>();

        String id = idFuture.get(10, TimeUnit.SECONDS); //wait for 10 seconds

        return (List<String>) Response.status(201).entity("The Token was successfully generated: " + id).build();

    } catch (Exception err) {
        return (List<String>) Response.status(400).entity(err.getMessage()).build();
    }
    }


    @Override
    public void subscribeEvent(Message message) throws Exception {
        if (message.getEventType().equals(TokenConfig.RETURN_TOKEN) && message.getService().equals("TokenResources")) {
            Gson gson = new Gson();
            receivedId = gson.fromJson(gson.toJson(message.getPayload()[0]), String.class);
            idFuture.complete(receivedId);
        }
    }

    private String waitForIdOrRetrieveIt() throws InterruptedException {
        int attempts = 0;
        while (receivedId == null && attempts < 5) {
            Thread.sleep(1);
            attempts++;
        }
        return receivedId;
    }
}
