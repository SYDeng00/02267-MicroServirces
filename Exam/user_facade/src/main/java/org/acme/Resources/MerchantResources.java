package org.acme.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.Models.User;
import org.acme.Services.UserService;

import java.util.List;
@Path("/")
public class MerchantResources {
    private UserService userService = new UserService();
    //Get All customer list
    @Path("/merchants")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listAllMerchant(){
        return userService.getAllMerchants();
    }

    //Add new customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String regNewMerchant(User user){
        int result = userService.addNewCustomer(user);
        if (result>0) return String.valueOf(result);
        else return "No User Created";
    }
}
