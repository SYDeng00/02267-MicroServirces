package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventPublisher;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class EventPublisher implements IEventPublisher {
    ConnectionFactory connectionfactory = new ConnectionFactory();
    Connection connection;
    Channel channel;
    DeliverCallback deliverCallback;
    Gson gson = new Gson();
    String queue = "payment_service";
    private String QUEUE_NAME="publisher_queue";
    
    private static final String ROUTING_KEY = "my_routing_key";
    private static final String EXCHANGE_NAME = "exchange_events";
    private static final String TOPIC = "event_topic";
    private static final String QUEUE_TYPE = "topic";


    @Override
    public void publishEvent(Message message) throws Exception {
        try{
            connectionfactory.setHost("localhost");
            // connectionfactory.setUsername("admin");
            // connectionfactory.setPassword("admin");
            // connectionfactory.setPort(15672);
            // connectionfactory.setVirtualHost("/");
            connection = connectionfactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);

           // channel.queueDeclare(message.getService(),false,false,false,null);
            String payload = new Gson().toJson(message);
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, QUEUE_NAME);
            System.out.println("Rabbit sending"+payload);
            channel.basicPublish(EXCHANGE_NAME,QUEUE_NAME,null,gson.toJson(message).getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
