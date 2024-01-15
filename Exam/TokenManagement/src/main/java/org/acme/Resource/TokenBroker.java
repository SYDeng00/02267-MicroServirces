package org.acme.Resource;

import com.google.gson.Gson;
import org.acme.Domain.Token;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

public class TokenBroker implements IEventSubscriber {
    EventPublisher eventPublisher = new EventPublisher();

    public void subscribeEvent(Message message) throws Exception {
        String event = message.getEventType();
        Object[] payload = message.getPayload();

        switch (event) {

            case TokenConfig.RETURN_TOKEN:
                Token token = typeTransfer(payload[0],Token.class);;
//
                String tokenList = "token is created";
                eventPublisher.publishEvent(new Message(TokenConfig.RETURN_TOKEN, "TokenResources",
                        new Object[] { token }));
                break;
            default:
                break;
        }



    }

    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(new TokenBroker());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }
}
