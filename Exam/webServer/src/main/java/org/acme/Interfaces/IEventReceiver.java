package org.acme.Interfaces;

import org.acme.Domains.Event;

public interface IEventReceiver {
    void sendEvent(Event event) throws Exception;
}
