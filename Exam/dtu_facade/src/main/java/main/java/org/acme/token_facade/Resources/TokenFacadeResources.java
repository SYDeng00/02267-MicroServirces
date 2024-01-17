package main.java.org.acme.token_facade.Resources;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventSubscriber;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Resources.AccountConfig;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.token_facade.Domains.Token_client;

@Path("/")
public class TokenFacadeResources {
    TokenFacadeBroker tokenFacadeBroker= new TokenFacadeBroker();

    @Path("tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token_client token) {
        try {
            
        tokenFacadeBroker.received();
            Token_client token_client = tokenFacadeBroker.createTokenForUser(token);
            return Response.status(201).entity(token_client).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
