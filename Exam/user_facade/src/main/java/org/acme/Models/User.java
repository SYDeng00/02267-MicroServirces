package org.acme.Models;


public class User {
    private  String id;
    private  String firstName;
    private  String lastName;
    private  String cpr;
    private  String bankAccount;
    private  String userType;


    public User(){}

    public User(String id, String firstName, String lastName, String cpr, String bankAccount, String userType){
        this.id = id;
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
        this.userType= userType;
    }
    public User(String firstName, String lastName, String cpr){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, String cpr, String bankAccount, String userType){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
        this.userType= userType;
    }

    public User(String firstName, String lastName, String cpr, String userType){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType= userType;
    }
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

