package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.Domain.Token;
import org.acme.service.TokenManagementServices;

import java.util.List;

@Path("/")
public class TokenResource {
TokenManagementServices tokenManagementServices = new TokenManagementServices();
    @Path("/tokens")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Token> getAllToken() {
        return tokenManagementServices.getAllTokenList();
    }
    @Path("/tokens/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTokenSet(Token token) {
        return tokenManagementServices.generateTokens(token);
    }
}
