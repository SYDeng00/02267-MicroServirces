package org.acme.Models;

import dtu.ws.fastmoney.User;

public class BankUser {
    private String cprNumber;
    private String lastName;
    private String firstName;

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BankUser(String cprNumber, String lastNumber,String firstNumber){
        this.cprNumber = cprNumber;
        this.lastName = lastNumber;
        this.firstName = firstNumber;
    }

}
