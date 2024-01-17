package main.java.org.acme.report_facade.Reposotories;

import main.java.org.acme.payment_facade.Interfaces.IPaymentFacadeRepositories;
import org.acme.Domains.Message;

import java.util.*;
/**
 * Configuration class for report-facade repository.
 * @author Tama Sarker
 */
public class ReportFacadeRepositories implements IPaymentFacadeRepositories {
    private static final ReportFacadeRepositories instance = new ReportFacadeRepositories();
    private static HashMap<UUID, Message> messages = new HashMap<>();

    private ReportFacadeRepositories() {}

    public static ReportFacadeRepositories getInstance() {
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
