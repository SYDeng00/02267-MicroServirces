package org.acme.Resoures;


import org.acme.Interfaces.IEventPublisher;
import org.acme.Interfaces.IEventSubscriber;

public class receiver{
    receiverrepo service = new receiverrepo();
    public void received() throws Exception{
        new org.acme.Resoures.EventSubscriber(service).subscribeEvent();
    }
    private void EventSubscriber(receiverrepo service) {
    }
    

}
