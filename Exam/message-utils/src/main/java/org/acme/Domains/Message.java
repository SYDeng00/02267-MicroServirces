package org.acme.Domains;

import java.util.UUID;

import jakarta.validation.Payload;

public class Message {
    private String evenType;
    private Object[] payload;
    private String status;
    private UUID messageID = UUID.randomUUID();
    

    private String event;
    private UUID requestID;
    private Callback callback = new Callback();
    
    private String service="default_service";


    
    public Message(String evenType, Object[] payload) {
        this.evenType = evenType;
        this.payload = payload;
    }
    public Callback getCallback() {
        return callback;
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    
    public UUID getRequestID() {
        return requestID;
    }
    public void setRequestID(UUID requestID) {
        this.requestID = requestID;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public Message(){

    }
    public Message(String service, String event){
        this.service = service;
        this.event = event;
    }
    public String getEvenType() {
        return evenType;
    }
    public void setEvenType(String evenType) {
        this.evenType = evenType;
    }
    public Object[] getPayload() {
        return payload;
    }
    public void setPayload(Object[] payload) {
        this.payload = payload;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
 
     public UUID getMessageID() {
        return messageID;
    }
    public void setMessageID(UUID messageID) {
        this.messageID = messageID;
    }


}
