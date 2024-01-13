package main.java.org.acme.payment_facade.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.acme.Domains.Message;

import main.java.org.acme.payment_facade.Interfaces.IPaymentFacadeRepositories;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class PaymentFacadeRepositories  implements IPaymentFacadeRepositories{
    static HashMap<UUID,Message> messages = new HashMap<>();

    @Override
    public void addMessage(Message message) {
        messages.put(message.getMessageID(),message);
    }

    @Override
    public Message getMessage(UUID MessageID) {
        return messages.get(MessageID);
    }

    @Override
    public List<Message> getMessages() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessage'");
    }

    @Override
    public void  removeMessage(UUID MessageID) {
        messages.remove(MessageID);
    }
}
