package main.java.org.acme.payment_facade.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.acme.Domains.Message;

import main.java.org.acme.payment_facade.Interfaces.IPaymentFacadeRepositories;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class PaymentFacadeRepositories implements IPaymentFacadeRepositories {
    private static final PaymentFacadeRepositories instance = new PaymentFacadeRepositories();
    private static HashMap<UUID, Message> messages = new HashMap<>();

    private PaymentFacadeRepositories() {}

    public static PaymentFacadeRepositories getInstance() {
        return instance;
    }

    @Override
    public void addMessage(Message message) {
        messages.put(message.getMessageID(), message);
    }

    @Override
    public Message getMessage(UUID MessageID) {
        return messages.get(MessageID);
    }

    @Override
    public List<Message> getMessages() {
        System.out.println("HashMap:");
        for (Map.Entry<UUID, Message> entry : messages.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        return new ArrayList<>(messages.values());
    }

    @Override
    public void removeMessage(UUID MessageID) {
        messages.remove(MessageID);
    }
}
