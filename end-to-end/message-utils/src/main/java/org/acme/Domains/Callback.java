package org.acme.Domains;



public class Callback {
    private String service;
    private Object[] payload;    
    
    public Callback(){
    }

    public Callback(String service, Object[] payload){
        this.service = service;
        this.payload = payload;
    }

    public void setService(String service){
        this.service = service;
    }

    public String getService(){
        return this.service;
    }

    public void setPayload(Object[] payload){
        this.payload = payload;
    }

    public Object[] getPayload(){
        return this.payload;
    }
}
