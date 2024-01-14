package org.acme.Interfaces;

import org.acme.Domains.Message;


public interface IEventSubscriber {
    void subscribeEvent(Message message) throws Exception;
}
