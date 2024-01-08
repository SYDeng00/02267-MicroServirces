// package org.acme.Services;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.acme.Models.Customer;
// import org.acme.Models.Trade;

// public class TradesService {
//     List<Trade> trades = new ArrayList<>(Arrays.asList(
//         new Trade( "Yingli",1500.0,"101 coffee bar",2500.0,300.0)
//     ));
//     CustomersService customersService = new CustomersService();
//     private int total=5;

//     private void addTotal(){
//         this.total+=1;
//     }
//     // public int getTotal(){
//     //     return this.total;
//     // }
//     public List<Trade> addTrade(Trade trade){
//         Double currentBalanceC = customersService.getCustomerAccountBalance(trade.getCustomerBankAccount()) - trade.getBalance();
//         Double currentBalanceM = customersService.getCustomerAccountBalance(trade.getCustomerBankAccount())+ trade.getBalance();
//         Trade newTrade = new Trade(trade.getCustomerBankAccount(),currentBalanceC,trade.getMerchantBankAccount(),currentBalanceM,trade.getBalance());
//         trades.add(newTrade);
//         customersService.changeAccountBalance(trade.getCustomerBankAccount(), currentBalanceC);
//         customersService.changeAccountBalance(trade.getCustomerBankAccount(), currentBalanceM);
//         return getAllTrades();
//     }

//     public List<Trade> getAllTrades(){
//         return trades;
//     }
//     public Boolean checkCustomer(String id){
//         Object rt = customersService.getCustomer(id);
//         if(rt instanceof Boolean){
            
//             return false;
//         }else{
//             return true;
//         }
//     }

//     public List<Trade> getAllRegUser() {
//         return trades;
//     }
// }
