package Domains;

import java.time.LocalDate;

public class Token {
    private String tokenID;
    private String token;
    private String tokenStatus;
    private LocalDate tokenCreateDate;
    private String customerID;
    private int tokenCount;

    public Token(String tokenID, String token, String tokenStatus, LocalDate tokenCreateDate, String customerID) {
        this.tokenID = tokenID;
        this.token = token;
        this.tokenStatus = tokenStatus;
        this.tokenCreateDate = tokenCreateDate;
        this.customerID = customerID;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Token(){}
    public Token(String customerID,int tokenCount) {
        this.customerID = customerID;
        this.tokenCount = tokenCount;
    }
    public Token(String tokenID, String token, String tokenStatus) {
        this.tokenID = tokenID;
        this.token = token;
        this.tokenStatus = tokenStatus;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }
}

