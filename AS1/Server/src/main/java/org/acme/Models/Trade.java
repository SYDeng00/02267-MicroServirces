package org.acme.Models;

public class Trade {
    private String customerBankAccount;
    private String merchantBankAccount;
    private Double balance;
    private Double customerBalance;
    private Double merchantBalance;
    public Trade(){}
    public Trade(String customerBankAccount,Double customerBalance, String merchantBankAccount,Double merchantBalance, Double balance) {
        this.customerBankAccount = customerBankAccount;
        this.customerBalance = customerBalance;
        this.merchantBalance = merchantBalance;
        this.merchantBankAccount = merchantBankAccount;
        this.balance = balance;
    }
    public Trade(String customerBankAccount, String merchantBankAccount,Double balance) {
        this.customerBankAccount = customerBankAccount;
        this.merchantBankAccount = merchantBankAccount;
        this.balance = balance;
    }
    public String getCustomerBankAccount() {
        return customerBankAccount;
    }
    public void setCustomerBankAccount(String customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }
    public String getMerchantBankAccount() {
        return merchantBankAccount;
    }
    public void setMerchantBankAccount(String merchantBankAccount) {
        this.merchantBankAccount = merchantBankAccount;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(Double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public Double getMerchantBalance() {
        return merchantBalance;
    }

    public void setMerchantBalance(Double merchantBalance) {
        this.merchantBalance = merchantBalance;
    }
}
