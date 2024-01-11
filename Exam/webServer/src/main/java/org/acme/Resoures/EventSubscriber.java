package org.acme.Resoures;

import org.acme.Domains.Callback;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;



import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class EventSubscriber implements IEventSubscriber {

    ConnectionFactory connectionfactory = new ConnectionFactory();
    Connection connection;
    Channel channel;
    IEventSubscriber service;
    Gson gson = new Gson();
    String queue = "payment_service";
    private String QUEUE_NAME="publisher_queue";
    private static final String EXCHANGE_NAME = "exchange_events";
    private static final String TOPIC = "event_topic";
    private static final String QUEUE_TYPE = "topic";
    DeliverCallback deliverCallback;
    public EventSubscriber(IEventSubscriber service){
        this.service = service;
    }
    @Override
    public void subscribeEvent() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
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
                e.getStackTrace();
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    @Override
    public void generateReply(Message message) {
        
    }

    

}
