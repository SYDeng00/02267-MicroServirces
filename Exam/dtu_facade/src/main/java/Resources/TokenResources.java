package Resources;


import Domains.Token;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Path("/")
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

    @Path("tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token token)  {
        try {
            // Publish the event
            publisher.publishEvent(new Message(TokenConfig.RETURN_TOKEN, "TokenBroker", new Object[] { token }));
            idFuture = new CompletableFuture<>();

            // Wait for the response
            List<String> tokens = Collections.singletonList(idFuture.get(10, TimeUnit.SECONDS)); // Assuming this returns a List<String>

            // Check if tokens are received
            if (tokens != null && !tokens.isEmpty()) {
                return Response.status(Response.Status.CREATED).entity(tokens).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Token generation failed").build();
            }

        } catch (Exception err) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err.getMessage()).build();
        }
    }


    @Override
    public void subscribeEvent(Message message)  {
        if (message.getEventType().equals(TokenConfig.RETURN_TOKEN) && message.getService().equals("TokenResources")) {
            Gson gson = new Gson();
            // Assuming the payload is a list of tokens
            List<String> tokens = gson.fromJson(gson.toJson(message.getPayload()[0]), new TypeToken<List<String>>(){}.getType());
            idFuture.complete(tokens.toString());
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
