package main.java.org.acme.token_facade.Resources;

import java.util.UUID;
import java.util.regex.Pattern;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.org.acme.token_facade.Domains.Token_client;

@Path("/")
public class TokenFacadeResources {
    TokenFacadeBroker tokenFacadeBroker = new TokenFacadeBroker();

    @Path("tokens")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenSet(Token_client token) {
        try {

            tokenFacadeBroker.received();
            Token_client token_client = tokenFacadeBroker.createTokenForUser(token);
            if (!isUUID(token_client.getTokens().get(0)))
                return Response.status(400).entity(token_client).build();

            return Response.status(200).entity(token_client).build();
        } catch (Exception err) {
            err.printStackTrace();
            return Response.status(400).entity(err.getMessage()).build();
        }
    }
     public static boolean isUUID(Object obj) {
        if (obj instanceof String) {
            String input = (String) obj;
            try {
                UUID uuid = UUID.fromString(input);
                return true; // If parsing as UUID succeeds, it's a valid UUID
            } catch (IllegalArgumentException e) {
                // If parsing fails, it's not a valid UUID
                return false;
            }
        } else {
            // If the object is not a String, it's not a valid UUID
            return false;
        }
    }

}
