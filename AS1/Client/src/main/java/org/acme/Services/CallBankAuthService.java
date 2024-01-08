package org.acme.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import dtu.ws.fastmoney.User;
import jakarta.jws.soap.SOAPBinding.Use;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import org.acme.Models.BankUser;
import org.acme.Models.Customer;

public class CallBankAuthService {
    BankService bank= new BankServiceService().getBankServicePort();
    List<String> usersAccount =  new ArrayList<>(Arrays.asList());
    
    List<BankUser> cfakeAccount = new ArrayList<>(Arrays.asList(
        new BankUser("cprnoYingli", "Yingli", "Duan"),
        new BankUser("cprnoTama","Tama","Sarker"),
        new BankUser("cprnoSiyuan","Siyuan","Liu"),

        new BankUser("CPR202CB", "202 coffee","bar"),
        new BankUser("CPR101CB", "101 coffee"," bar"),
        new BankUser("CPRCT","202 ", "canteen"),
        new BankUser("CPR101CT","101", "canteen")
    ));
    /**
     * 
     * @return
     */
    private List<User> getUsers(){
        Iterator<BankUser> it = cfakeAccount.iterator();
        List<User> newUsers = new ArrayList<>(Arrays.asList());
        while(it.hasNext()){
            BankUser bankUser = it.next();
            User user = new User();
            user.setCprNumber(bankUser.getCprNumber());
            user.setFirstName(bankUser.getFirstName());
            user.setLastName(bankUser.getLastName());
            newUsers.add(user);
        }
        return newUsers;
    }
    /**
     * 
     * @return
     * @throws BankServiceException_Exception
     */
    public List<String> CreateALLAccount()throws BankServiceException_Exception{
        List<User> newUsers = this.getUsers();
        Iterator<User> it = newUsers.iterator();
        List<User> rt = new ArrayList<>(Arrays.asList());
        while(it.hasNext()){
            User bankUser = it.next();
            BigDecimal balance = new BigDecimal(1000.0);
            String user = bank.createAccountWithBalance(bankUser, balance); 
            System.out.println(user);
            usersAccount.add(user);
        }
        return usersAccount;
    }
    /**
     * Create bank account for new customer
     * @param customer
     * @return
     * @throws BankServiceException_Exception
     */



    public String CreateOneAccount(Customer customer) throws BankServiceException_Exception{
        User bankUser = new User();
        bankUser.setCprNumber(customer.getCpr());
        bankUser.setFirstName(customer.getFirstName());
        bankUser.setLastName(customer.getLastName());
        BigDecimal balance = new BigDecimal(customer.getBalance());
        String accountId = bank.createAccountWithBalance(bankUser, balance); 
        return accountId;
    }
    /**
     * MUST DO, to make sure we can reuse the bank account in our test
     * @throws BankServiceException_Exception
     */
    public void retireBankAccount(String accountId) throws BankServiceException_Exception{
       
            bank.retireAccount(accountId);
       
    }
   
    /***
     * Valid if the user bank account is 
     * @param id
     * @return
     * @throws BankServiceException_Exception
     */
    public Boolean validAccount(String id) throws BankServiceException_Exception{
        try{
            Account rt =bank.getAccount(id);
            System.out.println(rt);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /***
     * 
     * @param debtor
     * @param creditor
     * @param amount
     * @param desc
     * @throws BankServiceException_Exception
     */
    public void transferMoneyFromTo(String debtor, String creditor, Double amount, String desc) throws BankServiceException_Exception{
        BigDecimal bigAmount = new BigDecimal(amount);
        bank.transferMoneyFromTo(debtor,creditor,bigAmount,desc);
    }
    /**
     * 
     * @param accountId
     * @return
     * @throws BankServiceException_Exception
     */
    public BigDecimal checkBalance(String accountId) throws BankServiceException_Exception{
        Account rt = bank.getAccount(accountId);
        return rt.getBalance();
    }


  
    /**
     * 
     * @param accountId
     * @return
     * @throws BankServiceException_Exception
     */
    public void deleteAccount(String accountId) throws BankServiceException_Exception{
        bank.retireAccount(accountId);
        
    }

}
