package main.java.org.acme.token_facade.Resources;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventSubscriber;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Domains.Token_client;
import Resources.AccountConfig;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/")
public class TokenFacadeResources implements IEventSubscriber {
	
	
	List<String> result = new ArrayList<>();

    private CompletableFuture<String> idFuture;
    CompletableFuture<List<String>> future = new CompletableFuture<>();
	public TokenFacadeResources(){
        try {
			subscriber.subscribeEvent("AccountResources");
		    CompletableFuture<List<String>> future = new CompletableFuture<>();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    EventSubscriber subscriber = new EventSubscriber(this); 
    @Path("tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token_client token)  {
        try {
            // Publish the event
           TokenFacadeBroker tokenBroker= new TokenFacadeBroker();
           tokenBroker.createTokenForUser(token);
           
           future = new CompletableFuture<>();
           List<String> tokens = future.get(10, TimeUnit.SECONDS);
            return Response.status(201).entity(tokens).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
    
    @Override
    public void subscribeEvent(Message message) {

        if (message.getEventType().equals(AccountConfig.RETURN_ID) && message.getService().equals("AccountResources")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {}.getType();
            result = gson.fromJson(gson.toJson(message.getPayload()[0]), listType);
            future.complete(result);
        }
    }


}
