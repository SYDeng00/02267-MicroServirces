package org.acme.Services;

import dtu.ws.fastmoney.BankServiceException_Exception;
import org.acme.Models.BankUser;
import org.acme.Models.Customer;
import org.acme.Models.Trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GeneralServices {
    List<Customer> customers = new ArrayList<>(Arrays.asList(
//            new Customer(1,"Yingli","lastName","cprnoYingli",1000.00,"db58c71a-087c-4dc5-914d-183298af6f7b","customer"),
//            new Customer(2,"Tama","lastName","cprnoTama",1000.00,"4f7c3f75-872c-456a-83ad-0917a0f9b725","customer"),
//            new Customer(3,"Siyuan","lastName","cprnoSiyuan",1000.00,"account_noSiyuan","customer"),
//            new Customer(4,"Jiahe","lastName","cprnoJiahe",1000.00,"account_noJiahe","customer"),
//            new Customer(5,"Xinyi","lastName","cprnoXinyi",1000.00,"account_noXinyi","customer")
        ));

        public List<Customer> getAllCustomers(){
            return customers;
        }
        private CallBankAuthService callBankAuthService = new CallBankAuthService();
        public boolean getCustomer(String bankAcc){
            Optional<Customer> opCustomers = customers.stream().filter(t->t.getBankAccount().equals(bankAcc)).findFirst();
            if(opCustomers.isPresent()){
                return true;
            }
            else{
                return false;
            }
        }
        public int addNewDtuPayUser(Customer newCustomer) throws BankServiceException_Exception {

            if (callBankAuthService.validAccount(newCustomer.getBankAccount())){
                int nextId = customers.size() + 1;
                newCustomer.setId(nextId);
                customers.add(newCustomer);
                return nextId;
            }
            else {
                return -1;
            }
        }
        public Double getCustomerAccountBalance(String accountNumber) {
            Optional<Customer> opCustomers = customers.stream().filter(t->t.getBankAccount().equals(accountNumber)).findFirst();
            if(opCustomers.isPresent()){
                return opCustomers.get().getBalance();
            }
            else{
                return 0.0;
            }
        }

        public void changeAccountBalance(String bankAccount, Double currentBalanceC) {
            for (Customer element: customers){
                if (element.getBankAccount().equals(bankAccount)){
                    element.setBalance(currentBalanceC);
                    customers.set(customers.indexOf(element), new Customer(element.getId(),element.getFirstName(),element.getLastName(),element.getCpr(),element.getBalance(),element.getBankAccount(),element.getUserType()));
                }
            }
        }
        List<Trade> trades = new ArrayList<>(Arrays.asList());

        public List<Trade> addTrade(Trade trade) throws BankServiceException_Exception {

            if (callBankAuthService.validAccount(trade.getCustomerBankAccount()) && callBankAuthService.validAccount(trade.getMerchantBankAccount())){
                callBankAuthService.transferMoneyFromTo(trade.getCustomerBankAccount(),trade.getMerchantBankAccount(),trade.getBalance(),"Transfered Money");
              
                Double currentBalanceC = getBalance(trade.getCustomerBankAccount()).doubleValue() ;
                Double currentBalanceM = getBalance(trade.getMerchantBankAccount()).doubleValue();
                Trade newTrade = new Trade(trade.getCustomerBankAccount(),currentBalanceC,trade.getMerchantBankAccount(),currentBalanceM,trade.getBalance());
                trades.add(newTrade);
                  changeAccountBalance(trade.getCustomerBankAccount(), currentBalanceC);
//                changeAccountBalance(trade.getMerchantBankAccount(), currentBalanceM);
                return getAllTrades();
            }
            return null;
        }

        public List<Trade> getAllTrades(){
            return trades;
        }

    public String addNewBankUser(Customer customer) throws BankServiceException_Exception {
            return callBankAuthService.CreateOneAccount(customer);
    }

    public BigDecimal getBalance(String accountId) throws BankServiceException_Exception{
        BigDecimal rt = callBankAuthService.checkBalance(accountId);
        System.out.println(rt);
        return rt;
    }


    public void deleteAccount(String accoutId) throws BankServiceException_Exception{
        callBankAuthService.deleteAccount(accoutId);
    }
}
