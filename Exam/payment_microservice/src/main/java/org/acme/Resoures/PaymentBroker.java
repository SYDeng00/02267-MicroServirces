package org.acme.Resoures;

import java.math.BigDecimal;
import java.util.UUID;
import org.acme.Domains.Message;
import org.acme.Domains.Payment;
import org.acme.Interfaces.IEventSubscriber;
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
            case PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT:
                UUID paymentID = UUID.randomUUID();
                UUID merchanUuid = typeTransfer(payload[0],UUID.class);
                String token = typeTransfer(payload[1],String.class);
                double amount = typeTransfer(payload[2],Double.class);
                System.out.println("Payment received message:"+PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT+"-->"+paymentID.toString()+merchanUuid.toString()+String.valueOf(amount));
                paymentRepository.addPayment(new Payment(
                    paymentID,
                    merchanUuid,
                    token,
                    new BigDecimal(amount)));
                eventPublisher.publishEvent(new Message(PaymentConfig.SEND_VALID_TOKENS,"TokenBroker",new Object[]{paymentID,token}));
                System.out.println("Payment send message:"+PaymentConfig.SEND_VALID_TOKENS);
                break;
            case PaymentConfig.RECEIVE_VALID_RESULT:
                boolean validResult = this.typeTransfer(payload[0], boolean.class);
                if(validResult){
                    Payment payment = paymentRepository.getPayment(typeTransfer(payload[0], UUID.class));
                    UUID merchaneUuid3 = typeTransfer(payload[0],UUID.class);
                    String token1 = typeTransfer(payload[1],String.class);
                   
                    eventPublisher.publishEvent(new Message(PaymentConfig.SEND_REQUEST_BANK_ACCOUNTS,"AccountBroker",new Object[]{payment.getPaymentID(),merchaneUuid3,token1}));
                }else{
                    //TODO
                }
                break;
            case PaymentConfig.RECEIVE_GET_ACCOUNTS:
                try {
                    BankService bank = new BankServiceService().getBankServicePort();
                    Payment payment = paymentRepository.getPayment(typeTransfer(payload[0],UUID.class));
                    UUID merchanUuid2 = typeTransfer(payload[1],UUID.class);
                    UUID customerUuid = typeTransfer(payload[2],UUID.class);
                    bank.transferMoneyFromTo(
                        merchanUuid2.toString(), 
                        customerUuid.toString(), 
                        payment.getAmount(),
                        "");
                    
                    eventPublisher.publishEvent(
                        new Message(
                            PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                            "ReportBroker",
                            new Object[]{
                                payment.getPaymentID(),
                                payment.getCustomerID(),
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
            subscriber.subscribeEvent(this.getClass().getSimpleName());
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
