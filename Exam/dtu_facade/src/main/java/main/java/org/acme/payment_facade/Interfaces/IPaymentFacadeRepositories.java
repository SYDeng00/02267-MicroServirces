package main.java.org.acme.payment_facade.Interfaces;

import java.util.List;
import java.util.UUID;

import org.acme.Domains.Message;

/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public interface IPaymentFacadeRepositories{
     void addMessage(Message message);
     Message getMessage(UUID MessageID);
    List<Message> getMessages();
    void removeMessage(UUID MessageID);
}
