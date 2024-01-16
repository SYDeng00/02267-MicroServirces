package main.java.org.acme.token_facade.Domains;

import java.util.ArrayList;
import java.util.List;


public class Token_client {
    private String customerID;
    private Integer token_number;
    private List<String> tokens = new ArrayList<>();

    // Constructor
    public Token_client() {}

    // Getter for customerID
    public String getCustomerID() {
        return customerID;
    }

    public Token_client(String customerID, List<String> tokens) {
        this.customerID = customerID;
        this.tokens = tokens;
    }

    public Token_client(String customerID, Integer token_number) {
        this.customerID = customerID;
        this.token_number = token_number;
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
