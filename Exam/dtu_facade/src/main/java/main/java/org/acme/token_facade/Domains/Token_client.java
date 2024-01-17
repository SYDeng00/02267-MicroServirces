package main.java.org.acme.token_facade.Domains;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Token_client {
    private UUID customerID;
    private Integer token_number;
    private List<UUID> tokens = new ArrayList<>();

    

    // Constructor
    public Token_client() {}

    

    public Token_client(UUID customerID, Integer token_number, List<UUID> tokens) {
        this.customerID = customerID;
        this.token_number = token_number;
        this.tokens = tokens;
    }



    public Token_client(UUID customerID, List<UUID> tokens) {
        this.customerID = customerID;
        this.tokens = tokens;
    }

    public Token_client(UUID customerID, Integer token_number) {
        this.customerID = customerID;
        this.token_number = token_number;
    }

    // Setter for customerID
    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public int getToken_number() {
        return token_number;
    }
    public void setToken_number(Integer token_number) {
        this.token_number = token_number;
    }

    public List<UUID> getTokens() {
        return tokens;
    }

    public void setTokens(List<UUID> tokens) {
        this.tokens = tokens;
    }

    // Getter for customerID
    public UUID getCustomerID() {
        return customerID;
    }
}
