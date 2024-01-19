//package Resources;
//
//import org.acme.Domains.Message;
//import org.acme.Resoures.EventPublisher;
//import org.acme.Resoures.EventSubscriber;
//
//import Domains.DTUPayAccount;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.POST;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//@Path("/")
//public class AccountResources {
//	@POST
//	@Path("accounts")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response registerAccount(DTUPayAccount account) {
//		try {
//			EventPublisher publisher = new EventPublisher();
//			publisher.publishEvent(new Message(AccountConfig.REGISTER, "AccountBroker",
//					new Object[] { account }));
//
//			
//			return Response.status(201).entity("The Account was successful - ").build();
//		} catch (Exception err) {
//			return Response.status(400).entity(err.getMessage()).build();
//		}
//	}
//
//}

package main.java.org.acme.account_facade.Resources;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import com.google.gson.Gson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.account_facade.Domains.DTUPayAccount;

/*
Author: Siyuan Deng s232275  
*/

@Path("/")
public class AccountResources implements IEventSubscriber {
	EventPublisher publisher = new EventPublisher();
	EventSubscriber subscriber = new EventSubscriber(this);
	String receivedId;

	private CompletableFuture<String> idFuture;

	public AccountResources() {

		try {
			subscriber.subscribeEvent("AccountResources");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@POST
	@Path("customers/accounts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN) // Specify that the server produces text/plain UUID
	public Response register_customerAccount(DTUPayAccount account) {
		try {
			idFuture = new CompletableFuture<>();
			publisher.publishEvent(new Message(AccountConfig.REGISTER, "AccountBroker", new Object[] { account }));
			idFuture.join();
			UUID uuid = UUID.fromString(receivedId);

			return Response.status(201).entity(uuid.toString()).build(); // Return UUID as a string
		} catch (Exception err) {
			return Response.status(400).entity(err.getMessage()).build();
		}
	}
	
	@POST
	@Path("merchant/accounts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN) // Specify that the server produces text/plain UUID
	public Response register_merchantAccount(DTUPayAccount account) {
		try {
			idFuture = new CompletableFuture<>();
			publisher.publishEvent(new Message(AccountConfig.REGISTER, "AccountBroker", new Object[] { account }));
			idFuture.join();
			UUID uuid = UUID.fromString(receivedId);

			return Response.status(201).entity(uuid.toString()).build(); // Return UUID as a string
		} catch (Exception err) {
			return Response.status(400).entity(err.getMessage()).build();
		}
	}

	@Override
	public void subscribeEvent(Message message) {
		String status = message.getStatus();
		if (message.getEventType().equals(AccountConfig.RETURN_ID) && message.getService().equals("AccountResources")) {
			Gson gson = new Gson();
			receivedId = gson.fromJson(gson.toJson(message.getPayload()[0]), String.class);

			idFuture.complete(status);
		}
	}

}
