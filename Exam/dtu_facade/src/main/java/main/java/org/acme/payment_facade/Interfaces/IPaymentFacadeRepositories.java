package main.java.org.acme.payment_facade.Interfaces;

import java.util.List;
import java.util.UUID;

import org.acme.Domains.Message;


public interface IPaymentFacadeRepositories{
     void addMessage(Message message);
     Message getMessage(UUID MessageID);
    List<Message> getMessages();
    void removeMessage(UUID MessageID);
}
