package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventPublisher;
import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author Yingli
 */
public class EventPublisher implements IEventPublisher{

    private static final String EXCHANGE_NAME = "exchange_events";

    public void publishEvent(Message message) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitMq");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String payload = new Gson().toJson(message);

            System.out.println("Rabbit sending " + payload);
            channel.basicPublish(EXCHANGE_NAME, message.getService(), null, payload.getBytes(StandardCharsets.UTF_8));
        }
    }
}