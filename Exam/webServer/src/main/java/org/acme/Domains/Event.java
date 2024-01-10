package org.acme.Domains;

import java.util.UUID;

public class Event {
    private String evenType;
    private Object[] arguement;
    private String status;
    private UUID eventID;
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
    public UUID getEventID() {
        return eventID;
    }
    public void setEventID(UUID eventID) {
        this.eventID = eventID;
    }
    

    
}
