package main.java.org.acme.token_facade.Resources;


import Domains.Token_client;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



@Path("/")
public class TokenFacadeResources {


    @Path("tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token_client token)  {
        try {
            // Publish the event
           TokenFacadeBroker tokenBroker= new TokenFacadeBroker();
//           tokenBroker.received();
           tokenBroker.createTokenForUser(token);
            return Response.status(201).entity("The token was created successfully - ").build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }

}
