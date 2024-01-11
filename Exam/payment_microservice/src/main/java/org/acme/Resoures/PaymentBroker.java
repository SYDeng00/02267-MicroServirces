package org.acme.Resoures;

import java.rmi.server.ObjID;
import java.util.UUID;

import org.acme.Domains.Payment;
import org.acme.Interfaces.IEventSubscriber;

import com.google.gson.Gson;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import io.cucumber.plugin.event.EventPublisher;
import org.acme.Resoures.Config;

public class PaymentBroker implements IEventSubscriber{

    EventPublisher eventPublisher = new EventPublisher();
    PaymentRepository paymentRepository = new PaymentRepository();
    @Override 
    public void subscribeEvent(Message message) throws Exception {
        // The message need to be import from message-utils
        String event = message.get("eventType");
        Object[] payload = message.get("payload");
        switch (event) {
            case MERCHANT_ASK_PAYMENT:
                UUID paymentID = UUID.randomUUID();
                paymentRepository.addPayment(new Payment(paymentID,payload[0],payload[1],payload[2]));
                eventPublisher.publish(new Messsage(VALID_TOKENS,new Obeject[]{payload[1]}));
                break;
            case VALID_RESULT:
    
                boolean validResult = this.typeTransfer(payload[0], boolean.class)
                if(validResult){
                   Payment payment = paymentRepository.getPayment(typeTransfer(payload[0], UUID.class));
                    
                    eventPublisher.publish(new Message(REQUEST_BANK_ACCOUNTS,new Object[]{payload[0],payload[1].getCustomerID() }));
                }else{
                    //TODO
                }
                break;
            case GET_ACCOUNTS:
                try {
                    BankService bank = new BankServiceService().getBankServicePort();
                    Payment payment = paymentRepository.getPayment(typeTransfer(payload[0],UUID.class));
                    bank.transferMoneyFromTo(payload[1], payload[2], payment.getAmount(),null);
                    eventPublisher.publish(new Message(UPDATE_PAYMENTS_REPORT,new Object(
                        payment.getPaymentID(),payment.getToekn(),getCustomerID(),payment.getAmount())));
                    paymentRepository.removePayment(payment.getPaymentID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            break;



                
            
            default:
                break;
        }
        
    }
    
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public PaymentBroker() throws Exception{
        try {
            EventSubscriber eventSubscriber = new EnventSubscriber(new PaymentBroker());
            eventSubscriber.subscribeEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
}
