package org.acme.Resoures;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.acme.Domains.Event;
import org.acme.Interfaces.IEventReceiver;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class EventReceiver implements IEventReceiver{

    ConnectionFactory connectionFactory = new ConnectionFactory();
    Connection connection;
    Channel channel;
    String QUEUE_NAME = "message_service";
    EventReceiver service;
    @Override
    public void receiveEvent(Event event) throws Exception {
        // TODO Auto-generated method stub
        DeliverCallback callback  = (consumerTag,delivery)->{
            String message = new String (delivery.getBody(),"UTF-8");
            System.out.println("[X] receiving" + message);

            event = new Gson().fromJson(message, Event.class);
            try{
                service.receiveEvent(event);
                
            }catch(Exception e){
                System.out.println(e.getMessage());
            }



        }

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, callback,);

        throw new UnsupportedOperationException("Unimplemented method 'sendEvent'");
    }

    public EventReceiver() throws IOException, TimeoutException{
        this.service = 
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }


    


    
}
