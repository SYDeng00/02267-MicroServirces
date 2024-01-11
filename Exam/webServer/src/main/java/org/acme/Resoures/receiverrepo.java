package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;

import jakarta.ws.rs.Path;

public class receiverrepo  implements IEventSubscriber{
    
    @Override
    public void subscribeEvent() throws Exception {
        this.generateReply(null);
    }
    
    @Override
    public void generateReply(Message message) {
        System.out.println("received");
    }
}
