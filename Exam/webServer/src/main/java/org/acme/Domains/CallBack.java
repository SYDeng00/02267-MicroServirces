package org.acme.Domains;



public class Callback {
    private String service;
    private String event;    
    
    
    public Callback(){

    }

    public Callback(String service, String event){
        this.service = service;
        this.event = event;
    }

    public void setService(String service){
        this.service = service;
    }

    public String getService(){
        return this.service;
    }

    public void setEvent(String event){
        this.event = event;
    }

    public String getEvent(){
        return this.event;
    }
}
