package org.acme.Interfaces;

import org.acme.Domains.Event;

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}
