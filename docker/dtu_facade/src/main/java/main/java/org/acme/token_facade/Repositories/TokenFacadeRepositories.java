package main.java.org.acme.token_facade.Repositories;

import main.java.org.acme.token_facade.Interfaces.ITokenFacadeRepositories;
import org.acme.Domains.Message;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TokenFacadeRepositories implements ITokenFacadeRepositories {

    static HashMap<UUID, Message> messages = new HashMap<>();
    static TokenFacadeRepositories tokenFacadeRepositories = new TokenFacadeRepositories();
    private TokenFacadeRepositories(){}
    public static TokenFacadeRepositories getInstance(){
        return tokenFacadeRepositories;
    }
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
