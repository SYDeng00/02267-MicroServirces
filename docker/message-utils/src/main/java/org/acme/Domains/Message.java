package org.acme.Domains;

import java.util.UUID;

public class Message {
    private String eventType;
    private Object[] payload;
    private String status = "200";
    private UUID messageID = UUID.randomUUID();

    private Callback callback = new Callback();

    private String service = null;

    public Message(String eventType, String service, Object[] payload) {
        this.eventType = eventType;
        this.payload = payload;
        this.service = service;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Message() {

    }

    public String getEventType() {
        return eventType;
    }

    public void setEvenType(String eventType) {
        this.eventType = eventType;
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
