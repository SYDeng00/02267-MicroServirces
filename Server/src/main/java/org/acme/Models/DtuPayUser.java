package org.acme.Models;

public class DtuPayUser {
    private int id;
    private String name;
    private String cpr;
    private String bankAccount;
    public DtuPayUser(){}
    public DtuPayUser(int id, String name, String cpr, String bankAccount) {
        this.id = id;
        this.name = name;
        this.cpr = cpr;
        this.bankAccount = bankAccount;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
