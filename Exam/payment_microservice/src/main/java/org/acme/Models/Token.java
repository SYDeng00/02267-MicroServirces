package org.acme.Models;

import java.util.UUID;

import org.eclipse.persistence.internal.oxm.schema.model.List;

import io.cucumber.java.it.Data;

public class Token {
    private Data expireData;
    private UUID tokenID;
    private UUID customerID;
    public Data getExpireData() {
        return expireData;
    }
    public void setExpireData(Data expireData) {
        this.expireData = expireData;
    }
    public UUID getTokenID() {
        return tokenID;
    }
    public void setTokenID(UUID tokenID) {
        this.tokenID = tokenID;
    }
    public UUID getCustomerID() {
        return customerID;
    }
    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }
    
}
