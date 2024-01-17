package main.java.org.acme.token_facade.Resources;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import com.google.gson.Gson;

import main.java.org.acme.token_facade.Domains.Token_client;
import main.java.org.acme.token_facade.Repositories.TokenFacadeRepositories;

public class TokenFacadeBroker implements IEventSubscriber {
	CompletableFuture<String> waitFormessageReply = new CompletableFuture<>();
	TokenFacadeRepositories tokenFacadeRepositories = TokenFacadeRepositories.getInstance();
	Message message;
	Token_client token_client; //= new Token_client();

	public Token_client createTokenForUser(Token_client token_client) {
		UUID costomerUuid = UUID.fromString(token_client.getCustomerID());
		int request_token_num = token_client.getToken_number();
		EventPublisher publisher = new EventPublisher();
		try {
			message = new Message(TokenFacadeConfig.SEND_RETURN_TOKEN,
					"TokenBroker",
					new Object[] { costomerUuid, request_token_num });
			publisher.publishEvent(message);
			tokenFacadeRepositories.addMessage(message);
			waitFormessageReply.join();
			return token_client;
		} catch (Exception e) {
			waitFormessageReply.complete("404");
			e.printStackTrace();
		}
		return this.token_client;
	}

	@Override
	public void subscribeEvent(Message message) throws Exception {
		Object[] payload = message.getPayload();
		String status = message.getStatus();
		String customerUuid = typeTransfer(payload[0], String.class);
		System.out.println("customerUuid:" + customerUuid);
		token_client = new Token_client(customerUuid,(List<String>) payload[1]);

		waitFormessageReply.complete(status);
		
	}

	public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
		Gson gson = new Gson();
		String json = gson.toJson(payload);
		return gson.fromJson(json, objectClass);
	}

	public void received() throws Exception {
		try {
			EventSubscriber subscriber = new EventSubscriber(this);
			subscriber.subscribeEvent(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
