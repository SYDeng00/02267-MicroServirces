package main.java.org.acme.token_facade.Resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.token_facade.Domains.Token_client;
/**
 * @author Yingli
 */
@Path("/")
public class TokenFacadeResources {
    TokenFacadeBroker tokenFacadeBroker= new TokenFacadeBroker();

    @Path("customers/tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token_client token) {
        try {
            
        tokenFacadeBroker.received();
            Token_client token_client = tokenFacadeBroker.createTokenForUser(token);
            return Response.status(200).entity(token_client).build();
        } catch (Exception err) {
        	err.printStackTrace();
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
}
