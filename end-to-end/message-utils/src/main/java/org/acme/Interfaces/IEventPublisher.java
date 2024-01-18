package org.acme.Interfaces;

import org.acme.Domains.Message;

public interface IEventPublisher {
    void publishEvent(Message message) throws Exception;

}
