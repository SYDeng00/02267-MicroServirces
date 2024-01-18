package main.java.org.acme.payment_facade.Resources;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import com.google.gson.Gson;

import main.java.org.acme.payment_facade.Domains.Payment;
import main.java.org.acme.payment_facade.Repositories.PaymentFacadeRepositories;

/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class PaymentFacadeBroker implements IEventSubscriber {
    CompletableFuture<String> waitFromessageReply ;
    PaymentFacadeRepositories paymentFacadeRepositories = PaymentFacadeRepositories.getInstance();
    Message message;
    String fianlStatus;
    public String sendPaymentRequestToPaymentService(Payment payment) {
        EventPublisher publisher = new EventPublisher();
        try {
            waitFromessageReply = new CompletableFuture<>();
            message = new Message(PaymentFacadeConfig.RECEIVE_MERCHANT_ASK_PAYMENT,
                    "PaymentBroker",
                    new Object[] { payment.getMerchantDtuPayID(),
                            payment.getToken(),
                            payment.getAmount() });
            System.out.println(this.message.getMessageID());
            publisher.publishEvent(message);
            paymentFacadeRepositories.addMessage(message);
            waitFromessageReply.join();
            
        } catch (Exception e) {
            waitFromessageReply.complete("404");
            fianlStatus = "404";
            e.printStackTrace();
        }
        return fianlStatus;
    }

    @Override
    public void subscribeEvent(Message message) throws Exception {
        Object[] payload = message.getPayload();
        String status = message.getStatus();
        UUID messageUuid = typeTransfer(payload[1], UUID.class);
        paymentFacadeRepositories.getMessages();
        System.out.println(status);
    
        fianlStatus = status;
        waitFromessageReply.complete(status);
        paymentFacadeRepositories.removeMessage(message.getMessageID());
    
    }

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(this);
            System.out.println(this.getClass().getSimpleName());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
