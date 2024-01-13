package org.acme.resources;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Repositories.PaymentRepository;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;
import org.jboss.logging.Logger;

import com.google.gson.Gson;


public class PaymentBroker implements IEventSubscriber {
    EventPublisher eventPublisher = new EventPublisher();
    PaymentRepository paymentRepository = new PaymentRepository();
    PaymentHandler paymentHandler = new PaymentHandler();

    private static final Logger LOG = Logger.getLogger(PaymentBroker.class);

    // Processes different types of payment-related messages.
    @Override
    public void subscribeEvent(Message message) throws Exception {
        // The message need to be import from message-utils
        String event = message.getEventType();
        Object[] payload = message.getPayload();
        switch (event) {
            case PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT:
            LOG.trace("-------------------------------\nPayment request received");
                paymentHandler.getPayment(payload);
                break;
            case PaymentConfig.RECEIVE_VALID_RESULT:
            LOG.trace("-------------------------------\nToken validaion response received");
                paymentHandler.getTokenValidResult(payload);
                break;
            case PaymentConfig.RECEIVE_GET_ACCOUNTS:
            LOG.trace("-------------------------------\nBank accounts response received");
                paymentHandler.getBankAccount(payload);
                break;
            case PaymentConfig.RECEIVE_REFUND_REQUEST:
            LOG.trace("-------------------------------\nRefund request received");
                paymentHandler.getRefund(payload);
            default:
                // Default case for unhandled events.
                break;
        }
    }

    // Static method for converting an object to a specified type using Gson.
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void received() throws Exception {
        try {
            EventSubscriber subscriber = new EventSubscriber(new PaymentBroker());
            subscriber.subscribeEvent(this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
