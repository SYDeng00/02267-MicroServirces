package org.acme.Interfaces;

import org.acme.Domains.Event;

public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
