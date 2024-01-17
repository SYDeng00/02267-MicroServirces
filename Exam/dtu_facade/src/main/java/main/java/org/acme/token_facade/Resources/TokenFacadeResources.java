package main.java.org.acme.token_facade.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


import com.google.gson.Gson;

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
            Object[] result = tokenFacadeBroker.createTokenForUser(token);
           
            int status = typeTransfer(result[0],Integer.class);
            if(status == 200){
                 List<UUID> tokens = new ArrayList<>();
                 if (status == 200) {
                	  

                	    // Check if result[1] is an instance of List
                	    if (result[1] instanceof List<?>) {
                	        List<?> resultList = (List<?>) result[1];

                	        // Process the elements of the list
                	        for (Object element : resultList) {
                	            System.out.println(element);

                	            // Check if the element is an instance of UUID
                	            if (element instanceof UUID) {
                	                tokens.add((UUID) element);
                	            }
                	        }
                	    }

                	    return Response.status(200).entity(tokens).build();
                	} else {
                	    // Handle non-200 status
                	    return Response.status(400).entity(typeTransfer(result[1], String.class)).build();
                	}        
            }
                
                else{
                    return Response.status(400).entity(typeTransfer(result[1],String.class)).build();
                }
        } catch (Exception err) {
            err.printStackTrace();
            return Response.status(400).entity(err.getMessage()).build();
        }
    }

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
		Gson gson = new Gson();
		String json = gson.toJson(payload);
		return gson.fromJson(json, objectClass);
	}

}
