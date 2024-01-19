package main.java.org.acme.account_facade.Domains;
 
/*
Author: Siyuan Deng s232275  
*/
public class Token_client {
    private String customerID;
    private Integer token_number;
 
    // Constructor
    public Token_client() {}
 
    // Getter for customerID
    public String getCustomerID() {
        return customerID;
    }
 
    // Setter for customerID
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
 
    // Getter for token_number
    public Integer getTokenNumber() {
        return token_number;
    }
 
    // Setter for token_number
    public void setTokenNumber(Integer token_number) {
        this.token_number = token_number;
    }
}
 