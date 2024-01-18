package org.acme.Resources;

import io.quarkus.logging.Log;
import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventSubscriber;
import org.jboss.logging.Logger;

/**
 * This is message broker. By implements IEventSubscriber,
 * subscribeEvent() can act as the callback function in EventSubscriber method
 *
 * @author Yingli
 * @version 1.0
 */
public class PaymentBroker implements IEventSubscriber {
    PaymentHandler paymentHandler = new PaymentHandler();

    private static final Logger LOG = Logger.getLogger(PaymentBroker.class);

    // Processes different types of payment-related messages.
    @Override
    public void subscribeEvent(Message message) throws Exception {
        // The message need to be import from message-utils
        String event = message.getEventType();
        Object[] payload = message.getPayload();
        LOG.info("Event type:" + event);
        Log.info(PaymentConfig.RECEIVE_GET_ACCOUNTS+ PaymentConfig.RECEIVE_GET_ACCOUNTS.equals(event));
        switch (event) {
            case PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT:
                LOG.info("-------------------------------Payment request received");
                paymentHandler.getPaymentRequest(payload);
                break;
            case PaymentConfig.RECEIVE_VALID_RESULT:
                LOG.info("-------------------------------Token validaion response received");
                paymentHandler.getTokenValidResult(payload);
                break;
            case PaymentConfig.RECEIVE_GET_ACCOUNTS:
                LOG.info("-------------------------------Bank accounts response received");
                paymentHandler.getBankAccount(payload);
                break;
            case PaymentConfig.RECEIVE_REFUND_REQUEST:
                LOG.info("-------------------------------Refund request received");
                paymentHandler.getRefund(payload);
            default:
                break;
        }
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
