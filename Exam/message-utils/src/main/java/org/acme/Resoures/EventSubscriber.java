package org.acme.Resoures;

import org.acme.Domains.Callback;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;



import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class EventSubscriber {

    ConnectionFactory connectionfactory = new ConnectionFactory();
    Connection connection;
    Channel channel;
    IEventSubscriber service;
    Gson gson = new Gson();
    private String QUEUE_NAME="publisher_queue";

    private static final String EXCHANGE_NAME = "exchange_events";
    private IEventSubscriber callback;
    DeliverCallback deliverCallback;
    public  EventSubscriber(IEventSubscriber callback){
        this.callback = callback;
    }

    public void subscribeEvent(String className) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, className);


        deliverCallback = (consumerTag, delivery) -> {
            String request = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] receiving "+request);
            Message message = new Gson().fromJson(request, Message.class);
            Callback callback = new Callback(message.getService(),message.getPayload());
            message.setCallback(callback);
            try {
                System.out.println("In consume");
                this.callback.subscribeEvent(message);
            } catch (Exception e) {
                e.getStackTrace();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    

}
