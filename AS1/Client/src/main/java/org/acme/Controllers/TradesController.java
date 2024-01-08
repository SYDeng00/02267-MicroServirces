// package org.acme.Controllers;


// import java.util.List;

// // import org.acme.Exceptions.MerchantNotExistException;
// import org.acme.Models.Trade;
// import org.acme.Services.CustomersService;
// import org.acme.Services.TradesService;

// import jakarta.ws.rs.Consumes;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.POST;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.MediaType;

// @Path("/simpleDTUPay")
// public class TradesController {
    
//     //private static final Exception MerchantNotExistException = null;
//     private TradesService tradesService = new TradesService();
//     private CustomersService customersService = new CustomersService();

    

//     @POST
//     @Consumes(MediaType.APPLICATION_JSON)
//     @Produces(MediaType.APPLICATION_JSON)
//     public List<Trade> addNewTrades(Trade trade) /*throws MerchantNotExistException*/{
        
//         return tradesService.addTrade(trade);
//     }

//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public List<Trade> getAllTrades(){
//         return tradesService.getAllTrades();
//     }


// }
