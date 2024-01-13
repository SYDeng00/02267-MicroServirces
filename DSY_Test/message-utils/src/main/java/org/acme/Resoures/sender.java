package org.acme.Resoures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.acme.Domains.Message;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class sender {
    private EventPublisher publisher = new EventPublisher();
    List<String> token = new ArrayList<>(Arrays.asList("String1","String2"));
    
    @GET
    @Path("send")
    public void sender() throws Exception{
        publisher.publishEvent(new Message("sender","receive2",new Object[]{token}));        
    }
    
    @GET
    @Path("repo")
    public void repo() throws Exception{
        receiverrepo re = new receiverrepo();
        re.received();
    }

    @GET
    @Path("r2")
    public void r2() throws Exception{
        receive2 r2 = new receive2();
        r2.received();
    }
    
}
