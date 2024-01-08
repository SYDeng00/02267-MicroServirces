package org.acme.Controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.Models.Customer;
import org.acme.Models.DtuPayUser;
import org.acme.Services.CustomersService;

import java.util.List;

@Path("/dtupaycustomer")
public class CustomerController {
    private CustomersService customersService = new CustomersService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> listRegDtuPayUser(){
        return customersService.getAllCustomers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String regDtuPayUser(Customer customer){
        int result = customersService.addNewDtuPayUser(customer);
        if (result>0) return String.valueOf(result);
        else return "User is not found or No bank Account found";
    }
}
