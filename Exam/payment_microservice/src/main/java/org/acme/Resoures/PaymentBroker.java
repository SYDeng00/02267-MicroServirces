package org.acme.Resoures;

import java.math.BigDecimal;
import java.rmi.server.ObjID;
import java.util.UUID;
import org.acme.Domains.Message;
import org.acme.Domains.Payment;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Models.Token;
import com.google.gson.Gson;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

public class PaymentBroker implements IEventSubscriber{
    EventPublisher eventPublisher = new EventPublisher();
    PaymentRepository paymentRepository = new PaymentRepository();
    @Override 
    public void subscribeEvent(Message message) throws Exception {
        // The message need to be import from message-utils
        String event = message.getEventType();
        Object[] payload = message.getPayload();
        switch (event) {
            case PaymentConfig.MERCHANT_ASK_PAYMENT:
                UUID paymentID = UUID.randomUUID();
                UUID merchanUuid = typeTransfer(payload[0],UUID.class);
                Token token = typeTransfer(payload[1],Token.class);
                double amount = typeTransfer(payload[2],Double.class);
                paymentRepository.addPayment(new Payment(
                    paymentID,
                    merchanUuid,
                    token,
                    amount));
                eventPublisher.publishEvent(new Message(PaymentConfig.VALID_TOKENS,new Object[]{token}));
                break;
            case PaymentConfig.VALID_RESULT:
                boolean validResult = this.typeTransfer(payload[0], boolean.class);
                if(validResult){
                    Payment payment = paymentRepository.getPayment(typeTransfer(payload[0], UUID.class));
                    UUID merchaneUuid3 = typeTransfer(payload[0],UUID.class);
                    Token token1 = typeTransfer(payload[1],Token.class);
                   
                    eventPublisher.publishEvent(new Message(PaymentConfig.REQUEST_BANK_ACCOUNTS,new Object[]{merchaneUuid3,token1}));
                }else{
                    //TODO
                }
                break;
            case PaymentConfig.GET_ACCOUNTS:
                try {
                    BankService bank = new BankServiceService().getBankServicePort();
                    Payment payment = paymentRepository.getPayment(typeTransfer(payload[0],UUID.class));
                    UUID merchanUuid2 = typeTransfer(payload[1],UUID.class);
                    UUID customerUuid = typeTransfer(payload[2],UUID.class);
                    bank.transferMoneyFromTo(
                        merchanUuid2.toString(), 
                        customerUuid.toString(), 
                        new BigDecimal(payment.getAmount()),
                        "");
                    
                    eventPublisher.publishEvent(
                        new Message(
                            PaymentConfig.UPDATE_PAYMENTS_REPORT,
                            new Object[]{
                                payment.getPaymentID(),
                                payment.getToken().getCustomerID(),
                                payment.getAmount()}));
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

    public void received() throws Exception{
        try{
            EventSubscriber subscriber = new EventSubscriber(new PaymentBroker());
            subscriber.subscribeEvent();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaymentBroker() throws Exception{
        // try {
        //     EventSubscriber eventSubscriber = new EventSubscriber(new PaymentBroker());
        //     eventSubscriber.subscribeEvent();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}
