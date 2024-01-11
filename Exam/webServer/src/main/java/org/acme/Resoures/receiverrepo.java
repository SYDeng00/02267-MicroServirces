package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;

import jakarta.ws.rs.Path;

public class receiverrepo  implements IEventSubscriber{
    
    @Override
    public void subscribeEvent() throws Exception {
        System.out.println("received");
    }

    public void received() throws Exception{
        try{
            EventSubscriber subscriber = new EventSubscriber(new receiverrepo());
            subscriber.subscribeEvent();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
