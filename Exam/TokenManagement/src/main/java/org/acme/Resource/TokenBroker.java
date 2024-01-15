package org.acme.Resource;

import com.google.gson.Gson;
import io.quarkus.logging.Log;
import org.acme.Domain.Token;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;
import org.acme.business_logic.TokenManagementServices;

import java.util.logging.Logger;

public class TokenBroker implements IEventSubscriber {
    Token token = new Token();
    TokenManagementServices services = new TokenManagementServices();
    private static final Logger LOG = Logger.getLogger(String.valueOf(TokenBroker.class));
    public void subscribeEvent(Message message) throws Exception {
        String event = message.getEventType();
        Object[] payload = message.getPayload();
        LOG.info("Event type:" + event);
        Log.info(TokenConfig.RETURN_TOKEN.equals(event));
        switch (event) {

            case TokenConfig.RETURN_TOKEN:
                LOG.info("-------------------------------Payment request received");
                services.generateTokens(token,payload);
                /*eventPublisher.publishEvent(new Message(TokenConfig.RETURN_TOKEN, "TokenResources",
                        new Object[] { token }));*/
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
