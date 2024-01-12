// package main.java.org.acme.payment_facade;

// import business_logic.PaymentRepository;
// import messaging.EventSender;
// import messaging.rabbitmq.RabbitMqListener;
// import messaging.rabbitmq.RabbitMqSender;
// import org.jboss.logging.Logger;

// public class PaymentService {

//     static PaymentRepository service=null;

//     public PaymentRepository getService(){

//         if(service!= null){
//             return service;
//         }

//         EventSender eventSender=new RabbitMqSender();
//         service = new PaymentRepository(eventSender);

//         RabbitMqListener r = new RabbitMqListener(service);

//         try {
//             r.listen();
//         } catch (Exception e) {
//             throw new Error(e);
//         }

//         return service;
//     }
// }