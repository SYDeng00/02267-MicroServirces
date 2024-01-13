package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;


public class receive2  implements IEventSubscriber{
    
    @Override
    public void subscribeEvent(Message message) throws Exception {
        System.out.println(message);
        //Input your own logic to cope with the message
        System.out.println("received 2");
    }

    public void received() throws Exception{
        try{
            EventSubscriber subscriber = new EventSubscriber(new receiverrepo());
            System.out.println(this.getClass().getSimpleName());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
