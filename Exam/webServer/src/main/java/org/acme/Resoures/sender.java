package org.acme.Resoures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.acme.Domains.Message;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/send")
public class sender {
    private EventPublisher publisher = new EventPublisher();
    List<String> token = new ArrayList<>(Arrays.asList("String1","String2"));
    
    @GET
    public void sender() throws Exception{
        publisher.publishEvent(new Message("sender",new Object[]{token}));
    }
    
    
}
