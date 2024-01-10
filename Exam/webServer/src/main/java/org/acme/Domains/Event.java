package org.acme.Domains;

import java.util.UUID;

import jakarta.validation.Payload;

public class Event {
    private String evenType;
    private Object[] arguement;
    private String status;
    private UUID messageID = UUID.randomUUID();
    public Payload payload;
    
    //private UUID eventID;
    private String service;
    public Callback getCallback() {
        return callback;
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    private String event;
    private UUID requestID;
    private Callback callback = new Callback();
    
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
    public Event(){

    }
    public Event(String service, String event){
        this.service = service;
        this.event = event;
    }
    public String getEvenType() {
        return evenType;
    }
    public void setEvenType(String evenType) {
        this.evenType = evenType;
    }
    public Object[] getArguement() {
        return arguement;
    }
    public void setArguement(Object[] arguement) {
        this.arguement = arguement;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    // public UUID getEventID() {
    //     return eventID;
    // }
    // public void setEventID(UUID eventID) {
    //     this.eventID = eventID;
    // }
     public UUID getMessageID() {
        return messageID;
    }
    public void setMessageID(UUID messageID) {
        this.messageID = messageID;
    }

    public Payload getPayload() {
        return payload;
    }
    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
