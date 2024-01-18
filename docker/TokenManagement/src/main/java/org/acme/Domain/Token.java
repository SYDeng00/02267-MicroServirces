package org.acme.Domain;


import java.time.LocalDate;
import java.util.UUID;

public class Token {
    private UUID token;
    private String tokenStatus;
    private LocalDate tokenCreateDate;
    private UUID customerID;
    private int tokenCount;

    private boolean valid = true;

    public Token( UUID token, String tokenStatus, LocalDate tokenCreateDate, UUID customerID) {
   
        this.token = token;
        this.tokenStatus = tokenStatus;
        this.tokenCreateDate = tokenCreateDate;
        this.customerID = customerID;
    }



    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public LocalDate getTokenCreateDate() {
        return tokenCreateDate;
    }

    public void setTokenCreateDate(LocalDate tokenCreateDate) {
        this.tokenCreateDate = tokenCreateDate;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public Token(){}
    public Token(UUID customerID,int tokenCount) {
        this.customerID = customerID;
        this.tokenCount = tokenCount;
    }
    public Token( UUID token, String tokenStatus) {

        this.token = token;
        this.tokenStatus = tokenStatus;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }

    public void setValid(boolean tokenStatus) {
        this.valid = tokenStatus;
    }
    public Boolean getValid(){
        return valid;
    }
    public void setValid(Boolean valid){
        this.valid = valid;
    }
}