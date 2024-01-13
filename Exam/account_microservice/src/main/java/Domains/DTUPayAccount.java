package Domains;

public class DTUPayAccount {
    String firstName;
    String lastName;
    String cpr;
    String bankID;

    public DTUPayAccount() {}

    public DTUPayAccount(String firstName, String lastName, String cpr, String bankID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        this.bankID=bankID;
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

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }
}