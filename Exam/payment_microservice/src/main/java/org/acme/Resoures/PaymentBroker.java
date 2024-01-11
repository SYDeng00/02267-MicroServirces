package org.acme.Resoures;

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
                Object[] rt = message.getPayload();
                Gson gson = new Gson();
                String jsonString = gson.toJson(rt[0]);
                boolean validResult = gson.fromJson(jsonString, boolean.class);
                if(validResult){
                    BankService bank = new BankServiceService().getBankServicePort();
                    Payment payment = paymentRepository.getPayment(typeTransfer(payload[0], UUID.class));
                    bank.transferMoneyFromTo();
                    
                }else{

                }
                eventPublisher.publish(new Message(VALID_RESULT,));
            
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
