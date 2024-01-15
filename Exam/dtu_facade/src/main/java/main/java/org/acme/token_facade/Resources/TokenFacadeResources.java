package main.java.org.acme.token_facade.Resources;


import main.java.org.acme.token_facade.Domains.Token;
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



@Path("/")
public class TokenFacadeResources {


    @Path("tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token token)  {
        try {
            // Publish the event
           TokenFacadeBroker tokenBroker= new TokenFacadeBroker();
           tokenBroker.received();
           tokenBroker.createTokenForUser(token);
            return Response.status(201).entity("The token was created successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }

}
