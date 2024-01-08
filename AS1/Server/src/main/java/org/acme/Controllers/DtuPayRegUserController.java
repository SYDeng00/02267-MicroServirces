// package org.acme.Controllers;


// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import org.acme.Models.DtuPayUser;
// import org.acme.Models.Trade;
// import org.acme.Services.RegDtuPayUserService;

// import java.util.List;

// @Path("/dtupayreg")
// public class DtuPayRegUserController {
//     private RegDtuPayUserService dtuPayUserService = new RegDtuPayUserService();
//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public List<DtuPayUser> listRegDtuPayUser(){
//         return dtuPayUserService.getAllDtuPayUser();
//     }

//     @POST
//     @Consumes(MediaType.APPLICATION_JSON)
//     @Produces(MediaType.APPLICATION_JSON)
//     public int regDtuPayUser(DtuPayUser dtuPayUser){
//         return dtuPayUserService.addNewDtuPayUser(dtuPayUser);
//     }
// }
