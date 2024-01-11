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
    private static final String EXCHANGE_NAME = "exchange_events";
    private static final String TOPIC = "event_topic";
    private static final String QUEUE_TYPE = "topic";


    @Override
    public void publishEvent(Message message) throws Exception {
        try{
            connectionfactory.setHost("rabbitmq");
            connection = connectionfactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, TOPIC);

           // channel.queueDeclare(message.getService(),false,false,false,null);
            String payload = new Gson().toJson(message);
            System.out.println("Rabbit sending"+payload);
            channel.basicPublish(this.QUEUE_NAME,message.getService(),null,gson.toJson(message).getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
