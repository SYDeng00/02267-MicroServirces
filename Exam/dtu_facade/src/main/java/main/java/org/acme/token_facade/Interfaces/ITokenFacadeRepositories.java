package main.java.org.acme.token_facade.Interfaces;

import org.acme.Domains.Message;

import java.util.List;
import java.util.UUID;

public interface ITokenFacadeRepositories {

    void addMessage(Message message);
    Message getMessage(UUID MessageID);
    List<Message> getMessages();
    void removeMessage(UUID MessageID);
}
