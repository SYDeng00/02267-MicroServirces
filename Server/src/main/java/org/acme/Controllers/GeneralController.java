package org.acme.Controllers;
import dtu.ws.fastmoney.BankServiceException_Exception;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.acme.Models.BankUser;
import org.acme.Models.Customer;
import org.acme.Models.Trade;
import org.acme.Services.CallBankAuthService;
import org.acme.Services.GeneralServices;

import java.math.BigDecimal;
import java.util.List;


@Path("/")
public class GeneralController {
    private GeneralServices generalServices = new GeneralServices();
    @Path("/bank")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String regBankUser(Customer customer) throws BankServiceException_Exception {
        String bAccNo = generalServices.addNewBankUser(customer);
        if (!bAccNo.isEmpty())
            return "Bank Account number =" + bAccNo;

        else return "No bank Account Created!!!";
    }
    @Path("/customers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> listRegDtuPayUser(){
        return generalServices.getAllCustomers();
    }

    
    @Path("/customers")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String regDtuPayUser(Customer customer) throws BankServiceException_Exception {
        int result = generalServices.addNewDtuPayUser(customer);
        if (result>0)
            return String.valueOf(result);

        else return "User is not Created";
    }

   //Trading Controllers
    @Path("/trades")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Trade> getAllTrades(){
        return generalServices.getAllTrades();
    }

    @Path("/trades")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Trade> addNewTrades(Trade trade) {
//        String debotor=trade.getCustomerBankAccount();
//        String creditor=trade.getMerchantBankAccount();
        try {
           return generalServices.addTrade(trade);
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Path("/getbalance")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public BigDecimal getBalance(String accoundId) throws BankServiceException_Exception{
        return generalServices.getBalance(accoundId);
    }
    @Path("/deleteaccount")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void deleteAccount(String accoundId) throws BankServiceException_Exception{
        generalServices.deleteAccount(accoundId);
    }
}
