package org.acme.Resoures;

import org.acme.Domains.Callback;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;



import com.google.gson.Gson;
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
    private static final String TOPIC = "event_topic";
    private static final String QUEUE_TYPE = "topic";
    private IEventSubscriber callback;
    DeliverCallback deliverCallback;
    public  EventSubscriber(IEventSubscriber callback){
        this.callback = callback;
    }

    public void subscribeEvent() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, QUEUE_NAME);


        deliverCallback = (consumerTag, delivery) -> {
            String request = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] receiving "+request);

            Message message = new Gson().fromJson(request, Message.class);
            Callback callback = new Callback(message.getService(),message.getEvent());
            message.setCallback(callback);
            try {
                System.out.println("In consume");
                this.callback.subscribeEvent();
            } catch (Exception e) {
                e.getStackTrace();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    

}
