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

public class PaymentFacadeBroker implements IEventSubscriber {
    CompletableFuture<String> waitFormessageReply = new CompletableFuture<>();
    PaymentFacadeRepositories paymentFacadeRepositories = new PaymentFacadeRepositories();
    Message message;

    public void sendPaymentRequestToPaymentService(Payment payment) {
        EventPublisher publisher = new EventPublisher();
        try {
            message = new Message(PaymentFacadeConfig.RECEIVE_MERCHANT_ASK_PAYMENT,
                    "PaymentBroker",
                    new Object[] { payment.getMerchantDtuPayID(),
                            payment.getToken(),
                            payment.getAmount() });
            publisher.publishEvent(message);
            // this.received();
            // wait for this
            waitFormessageReply.join();
        } catch (Exception e) {
            waitFormessageReply.complete("404");
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeEvent(Message message) throws Exception {
        Object[] payload = message.getPayload();
        String status = message.getStatus();
        UUID messageUuid = typeTransfer(payload[1], UUID.class);
        if (messageUuid.equals(this.message.getMessageID())) {
            waitFormessageReply.complete(status);
        }
        paymentFacadeRepositories.removeMessage(message.getMessageID());
    }

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(new PaymentFacadeBroker());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
