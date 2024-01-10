package org.acme.Interfaces;

import org.acme.Domains.Event;

public interface IEventSender {
    void receiveEvent(Event event) throws Exception;
}
