package main.java.org.acme.token_facade.Domains;

import java.util.ArrayList;
import java.util.List;


public class Token_client {
    private String customerID;
    private Integer token_number;
    private List<String> tokens = new ArrayList<>();

    public Integer getToken_number() {
        return token_number;
    }

    public void setToken_number(Integer token_number) {
        this.token_number = token_number;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

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
}
