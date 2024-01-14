package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventPublisher;
import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
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
    
    private static final String EXCHANGE_NAME = "exchange_events";

    @Override
    public void publishEvent(Message message) throws Exception {
        try{
            connectionfactory.setHost("localhost");
            connection = connectionfactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String payload = new Gson().toJson(message);

            System.out.println("Rabbit sending"+payload);
            channel.basicPublish(EXCHANGE_NAME,message.getService(),null,gson.toJson(message).getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
