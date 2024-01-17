package Domains;

import java.util.ArrayList;
import java.util.List;


public class Token_client {
    private String customerID;
    private Integer token_number;
    private List<String> tokens = new ArrayList<>();

    

    // Constructor
    public Token_client() {}

    

    public Token_client(String customerID, Integer token_number, List<String> tokens) {
        this.customerID = customerID;
        this.token_number = token_number;
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

    public int getToken_number() {
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

    // Getter for customerID
    public String getCustomerID() {
        return customerID;
    }
}
