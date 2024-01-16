package org.acme.Resoures;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;

import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

// public class EventSubscriber {

//     private static final String QUEUE_NAME = "msg_queue";
//     private static final String EXCHANGE_NAME = "exchange_events";
//     private final IEventSubscriber callback;
//     private final Gson gson = new Gson();

//     public EventSubscriber(IEventSubscriber callback) {
//         this.callback = callback;
//     }

//     public void subscribeEvent(String routingKey) throws Exception {
//         ConnectionFactory factory = new ConnectionFactory();
//         factory.setHost("localhost");
//         try (Connection connection = factory.newConnection();
//              Channel channel = connection.createChannel()) {

//             channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
//             channel.queueDeclare(QUEUE_NAME, true, false, false, null); // Declare the queue
//             channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey); // Bind the queue to the exchange

//             DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                 String messageJson = new String(delivery.getBody(), "UTF-8");
//                 System.out.println("[x] Receiving " + messageJson);
//                 Message message = gson.fromJson(messageJson, Message.class);
//                 Callback callbackObj = new Callback(message.getService(), message.getPayload());
//                 message.setCallback(callbackObj);
//                 try {
//                     System.out.println("In consume");
//                     callback.subscribeEvent(message);
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             };

//             channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
//         }
//     }
// }

public class EventSubscriber {

    private static final String EXCHANGE_NAME = "exchange_events";
    private static final String QUEUE_NAME = "msg_queue";
    private final IEventSubscriber callback;

    public EventSubscriber(IEventSubscriber callback) {
        this.callback = callback;
    }

    public void subscribeEvent(String routingKey) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(routingKey, true, false, false, null);
            channel.queueBind(routingKey, EXCHANGE_NAME, routingKey);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("[x] Receiving:\n");
                String messageJson = new String(delivery.getBody(), "UTF-8");
                System.out.println( messageJson);
                Message message = new Gson().fromJson(messageJson, Message.class);
                try {
                    System.out.println("In consume");
                    this.callback.subscribeEvent(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume(routingKey, true, deliverCallback, consumerTag -> {
            });
        }
    }
}
