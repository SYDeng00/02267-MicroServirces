package org.acme.Models;

public class Customer {
    private  int id;
    private  String firstName;
    private  String lastName;
    private  String cpr;
    private  Double balance;
    private  String bankAccount;
    private  String userType;


    public Customer(){}

    public Customer(int id,String firstName,String lastName,String cpr, Double balance, String bankAccount,String userType){
        this.id = id;
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.bankAccount = bankAccount;
        this.userType= userType;
    }
    public Customer(String firstName,String lastName,String cpr, Double balance, String bankAccount,String userType){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.bankAccount = bankAccount;
        this.userType= userType;
    }
    public Customer(String firstName,String lastName,String cpr, Double balance){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public Customer(String firstName,String lastName,String cpr,String bankAccount,String userType){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
        this.userType= userType;
    }

    public Customer(String firstName,String lastName,String cpr,Double balance,String userType){
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.userType= userType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
