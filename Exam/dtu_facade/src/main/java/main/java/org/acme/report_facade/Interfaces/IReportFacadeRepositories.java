package main.java.org.acme.report_facade.Interfaces;

import org.acme.Domains.Message;

import java.util.List;
import java.util.UUID;

/**
 * 
 * @author Tama
 * @version 1.0
 * 
 */
public interface IReportFacadeRepositories {
     void addMessage(Message message);
     Message getMessage(UUID MessageID);
    List<Message> getMessages();
    void removeMessage(UUID MessageID);
}
