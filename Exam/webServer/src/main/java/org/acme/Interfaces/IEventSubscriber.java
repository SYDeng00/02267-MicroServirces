package org.acme.Interfaces;

import org.acme.Domains.Message;


public interface IEventSubscriber {
    void subscribeEvent() throws Exception;
    void generateReply(Message message);
}
