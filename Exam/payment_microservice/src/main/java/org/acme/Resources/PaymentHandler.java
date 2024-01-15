package org.acme.Resources;

import java.math.BigDecimal;
import java.util.UUID;
import org.jboss.logging.Logger;
import org.acme.Domains.Message;
import org.acme.Domains.Payment;
import org.acme.Domains.Refund;
import org.acme.Repositories.PaymentRepository;
import org.acme.Resoures.EventPublisher;

import com.google.gson.Gson;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import io.quarkus.logging.Log;

/**
 * This class used for handle all message, 
 * provide corresponding functions to PaymentBroker
 * 
 * @author Yingli
 * @version 1.0
 * 
 */

public class PaymentHandler {
    EventPublisher eventPublisher = new EventPublisher();
    PaymentRepository paymentRepository =  PaymentRepository.getInstance();
    private static final Logger LOG = Logger.getLogger(PaymentHandler.class);

    
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    /**
     * 
     * @param payload
     * @throws Exception
     */
    public void getPayment(Object[] payload) throws Exception {
        UUID paymentID = UUID.randomUUID();
        UUID messageUuid = typeTransfer(payload[0], UUID.class);
        UUID merchanUuid = typeTransfer(payload[1], UUID.class);
        UUID token = typeTransfer(payload[2], UUID.class);
        double amount = typeTransfer(payload[3], Double.class);
        LOG.info("Payment information resolve succeed:" + PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT + "-->"
                + paymentID.toString() + merchanUuid.toString() + amount);
        paymentRepository.addPayment(new Payment(
                paymentID,
                messageUuid,
                merchanUuid,
                token,
                BigDecimal.valueOf(amount)));
        eventPublisher.publishEvent(
                new Message(PaymentConfig.SEND_VALID_TOKENS, "TokenBroker", new Object[] { paymentID, token }));
        LOG.info("Payment microservce send message to token microservce:" + PaymentConfig.SEND_VALID_TOKENS);
    }

     /**
     * 
     * @param payload
     * @throws Exception
     */
    public void getTokenValidResult(Object[] payload) throws Exception {
        boolean validResult = PaymentHandler.typeTransfer(payload[0], boolean.class);
        UUID paymentID = typeTransfer(payload[0], UUID.class);
        UUID merchaneUuid = typeTransfer(payload[1], UUID.class);
        Log.info("Toekn validaton information resolved:" + String.valueOf(validResult));
        if (validResult) {
            UUID customerUuid = typeTransfer(payload[2], UUID.class);
            paymentRepository.getPayment(paymentID).setCustomerID(customerUuid);
            eventPublisher.publishEvent(new Message(PaymentConfig.SEND_REQUEST_BANK_ACCOUNTS, "AccountBroker",
                    new Object[] { paymentID, merchaneUuid, customerUuid, "payment" }));
            Log.info(customerUuid);
        } else {
            String reason = typeTransfer(payload[3], String.class);
            Message message = new Message(PaymentConfig.SEND_PAYMENT_RESULT, "PaymentFacadeBroker",
                    new Object[] { paymentID, reason });
            message.setStatus("401");
            eventPublisher.publishEvent(message);
            LOG.error("Token validation failed:" + reason);
        }
    }

     /**
     * 
     * @param payload
     * @throws Exception
     */
    public void getBankAccount(Object[] payload) throws Exception {
        String payType = PaymentHandler.typeTransfer(payload[3], String.class);
        UUID payOrRefundUuid = PaymentHandler.typeTransfer(payload[0], UUID.class);
        UUID debetorBankAccount;
        UUID creditorBankAccount;
        BigDecimal amount;
        if (payType.equals("refund")) {
            Refund refund = paymentRepository.getRefund(payOrRefundUuid);
            debetorBankAccount = PaymentHandler.typeTransfer(payload[1], UUID.class);
            creditorBankAccount = PaymentHandler.typeTransfer(payload[2], UUID.class);
            amount = refund.getAmount();
        } else {
            Payment payment = paymentRepository.getPayment(payOrRefundUuid);
            debetorBankAccount = PaymentHandler.typeTransfer(payload[2], UUID.class);
            creditorBankAccount = PaymentHandler.typeTransfer(payload[1], UUID.class);
            amount = payment.getAmount();
        }
        try {
            BankService bank = new BankServiceService().getBankServicePort();

            bank.transferMoneyFromTo(
                    debetorBankAccount.toString(),
                    creditorBankAccount.toString(),
                    amount,
                    "");
            Message message = new Message(
                    PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                    "ReportBroker",
                    new Object[] {
                            payType,
                            payOrRefundUuid,
                            creditorBankAccount,
                            debetorBankAccount,
                            amount,
                        paymentRepository.getPayment(payOrRefundUuid).getToken() });
            message.setStatus("200");
            eventPublisher.publishEvent(message);

            message = new Message(
                    PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                    "PaymentFacadeBroker",
                    new Object[] {
                            paymentRepository.getPayment(payOrRefundUuid).getMessageId(),
                            payType,
                            payOrRefundUuid,
                            creditorBankAccount,
                            debetorBankAccount,
                            amount });
            message.setStatus("200");
            eventPublisher.publishEvent(message);
        } catch (Exception e) {
            Message message = new Message(
                    PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                    "ReportBroker",
                    new Object[] {
                            paymentRepository.getPayment(payOrRefundUuid).getMessageId(),
                            payType,
                            payOrRefundUuid,
                            creditorBankAccount,
                            debetorBankAccount,
                            amount });
            message.setStatus("404");
            eventPublisher.publishEvent(message);
            Log.error("Transfer failed");
            e.printStackTrace();
        } finally {
            if (payType.equals("refund")) {
                paymentRepository.removeRefund(payOrRefundUuid);
            } else {
                paymentRepository.removePayment(payOrRefundUuid);
            }
        }
    }

     /**
     * 
     * @param payload
     * @throws Exception
     */
    public void getRefund(Object[] payload) throws Exception {
        // {UUID->paymentID, UUID->merchantID, BigDecimal->amount}
        UUID paymentID = typeTransfer(payload[0], UUID.class);
        UUID merchantUuid = typeTransfer(payload[1], UUID.class);
        BigDecimal amount = typeTransfer(payload[2], BigDecimal.class);
        UUID refundId = UUID.randomUUID();
        paymentRepository.addRefund(new Refund(refundId, paymentID, merchantUuid, amount));
        UUID customerUuid = paymentRepository.getPayment(paymentID).getCustomerID();
        eventPublisher.publishEvent(new Message(PaymentConfig.SEND_REQUEST_BANK_ACCOUNTS, "AccountBroker",
                new Object[] { refundId, merchantUuid, customerUuid, "refund" }));
    }

}
