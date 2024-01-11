package org.acme.Resoures;

import org.acme.Domains.CallBack;
import org.acme.Domains.Callback;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;

import java.nio.charset.StandardCharsets;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class EventSubscriber implements IEventSubscriber {

    ConnectionFactory connectionfactory = new ConnectionFactory();
    Connection connection;
    Channel channel;
    Gson gson = new Gson();
    String queue = "payment_service";
    private String QUEUE_NAME="publisher_queue";
    private static final String EXCHANGE_NAME = "exchange_events";
    private static final String TOPIC = "event_topic";
    private static final String QUEUE_TYPE = "topic";
    DeliverCallback deliverCallback;

    public void subscribeEvent() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitMq");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, TOPIC);

        deliverCallback = (consumerTag, delivery) -> {
            String request = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] receiving "+request);

            Message message = new Gson().fromJson(request, Message.class);
            Callback callback = new Callback(message.getService(),message.getEvent());
            message.setCallback(callback);
            try {
                this.generateReply(message);
            } catch (Exception e) {
                throw new Error(e);
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    @Override
    public void generateReply(Message message) {
        
    }

    @Override
    public void subscribeEvent(Message message) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subscribeEvent'");
    }

}
