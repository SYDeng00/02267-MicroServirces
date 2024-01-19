package org.acme.Resource;

import com.google.gson.Gson;
import io.quarkus.logging.Log;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import java.util.logging.Logger;


/**
 * Author: Yingli, Divya
 */

public class TokenBroker implements IEventSubscriber {
    TokenManagementServices services = new TokenManagementServices();
    private static final Logger LOG = Logger.getLogger(String.valueOf(TokenBroker.class));
    EventPublisher eventPublisher = new EventPublisher();
    
    @Override
    public void subscribeEvent(Message message) throws Exception {
        String event = message.getEventType();
        Object[] payload = message.getPayload();
        Log.info(TokenConfig.RECEIVE_RETURN_TOKEN.equals(event));
        switch (event) {
            case TokenConfig.RECEIVE_RETURN_TOKEN:
                LOG.info("-------------------------------Payment request received");
                services.generateTokens(payload);
                break;
            case TokenConfig.RECEIVE_TOKEN_VALID:
                LOG.info("-------------------------------Token Validation request received");
                services.tokenValidate(payload);
            default:
                break;
        }
    }

    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(new TokenBroker());
            System.out.println("In received");
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