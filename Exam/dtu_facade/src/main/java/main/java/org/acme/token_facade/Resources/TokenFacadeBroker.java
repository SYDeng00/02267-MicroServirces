package main.java.org.acme.token_facade.Resources;

import com.google.gson.Gson;
import main.java.org.acme.payment_facade.Domains.Payment;
import main.java.org.acme.payment_facade.Resources.PaymentFacadeBroker;
import main.java.org.acme.payment_facade.Resources.PaymentFacadeConfig;
import main.java.org.acme.token_facade.Domains.Token;
import main.java.org.acme.token_facade.Repositories.TokenFacadeRepositories;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TokenFacadeBroker implements IEventSubscriber {

    CompletableFuture<String> waitFormessageReply = new CompletableFuture<>();

    TokenFacadeRepositories tokenFacadeRepositories = new TokenFacadeRepositories();

    Message message;

    public void createTokenForUser(Token token) {
        EventPublisher publisher = new EventPublisher();
        try {
            message = new Message(TokenFacadeConfig.RETURN_TOKEN,
                    "TokenBroker",
                    new Object[] { token.getToken() });
            publisher.publishEvent(message);
            waitFormessageReply.join();
        } catch (Exception e) {
            waitFormessageReply.complete("404");
            e.printStackTrace();
        }
    }
    @Override
    public void subscribeEvent(Message message) throws Exception {
        Object[] payload = message.getPayload();
        String status = message.getStatus();
        UUID messageUuid = typeTransfer(payload[1], UUID.class);
        if (messageUuid.equals(this.message.getMessageID())) {
            waitFormessageReply.complete(status);
        }
        tokenFacadeRepositories.removeMessage(message.getMessageID());
    }

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(new PaymentFacadeBroker());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
