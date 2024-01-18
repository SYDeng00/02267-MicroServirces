package org.acme.Resources;

import java.math.BigDecimal;
import java.util.UUID;

import dtu.ws.fastmoney.BankServiceException_Exception;
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
    PaymentRepository paymentRepository = PaymentRepository.getInstance();
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
    public void getPaymentRequest(Object[] payload) throws Exception {
        UUID paymentID = UUID.randomUUID();
        UUID merchanUuid = typeTransfer(payload[0], UUID.class);
        UUID token = typeTransfer(payload[1], UUID.class);
        double amount = typeTransfer(payload[2], Double.class);
        LOG.info("Payment information resolve succeed:" + PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT + "-->"
                + paymentID.toString() + merchanUuid.toString() + amount);
        paymentRepository.addPayment(new Payment(
                paymentID,
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
        Boolean validResult = PaymentHandler.typeTransfer(payload[1], Boolean.class);
        UUID paymentID = typeTransfer(payload[0], UUID.class);
        UUID customerUuid = typeTransfer(payload[3], UUID.class);
        Log.info("Toekn validaton information resolved:" + String.valueOf(validResult));
        if (validResult) {
            UUID merchantUuid = paymentRepository.getPayment(paymentID).getMerchantId();
            paymentRepository.getPayment(paymentID).setCustomerId(customerUuid);
            eventPublisher.publishEvent(new Message(PaymentConfig.SEND_REQUEST_BANK_ACCOUNTS, "AccountBroker",
                    new Object[] { paymentID, merchantUuid, customerUuid, "payment" }));
            Log.info(customerUuid);
        } else {
            String reason = typeTransfer(payload[2], String.class);
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
    public void getBankAccount(Object[] payload) {
        String payType = PaymentHandler.typeTransfer(payload[3], String.class);
        LOG.info("The request is:" + payType);
        UUID payOrRefundUuid = PaymentHandler.typeTransfer(payload[0], UUID.class);
        UUID token;
        String debetorBankAccount;
        String creditorBankAccount;
        UUID debetorID;
        UUID creditorID;
        BigDecimal amount;
        Payment payment;
        if (payType.equals("refund")) {
            Refund refund = paymentRepository.getRefund(payOrRefundUuid);
            debetorBankAccount = PaymentHandler.typeTransfer(payload[1], String.class);
            creditorBankAccount = PaymentHandler.typeTransfer(payload[2], String.class);
            amount = refund.getAmount();
            payment = paymentRepository.getPayment(paymentRepository.getRefund(payOrRefundUuid).getPaymentId());
            debetorID = payment.getCustomerId();
            creditorID = payment.getMerchantId();
            token = payment.getToken();
        } else {
            payment = paymentRepository.getPayment(payOrRefundUuid);
            debetorBankAccount = PaymentHandler.typeTransfer(payload[2], String.class);
            creditorBankAccount = PaymentHandler.typeTransfer(payload[1], String.class);
            amount = payment.getAmount();
            token = paymentRepository.getPayment(payOrRefundUuid).getToken();
            debetorID = payment.getMerchantId();
            creditorID = payment.getCustomerId();
        }
        try {
            BankService bank = new BankServiceService().getBankServicePort();
            bank.transferMoneyFromTo(
                    debetorBankAccount,
                    creditorBankAccount,
                    amount,
                    "dtu pay");
            Message message = new Message(
                    PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                    "ReportBroker",
                    new Object[] {
                            payType,
                            payOrRefundUuid,
                            creditorID,
                            debetorID,
                            amount,
                            token });
            message.setStatus("200");
            eventPublisher.publishEvent(message);

            message = new Message(
                    PaymentConfig.SEND_PAYMENT_RESULT,
                    "PaymentFacadeBroker",
                    new Object[] {
                            payType,
                            payOrRefundUuid,
                            creditorBankAccount,
                            debetorBankAccount,
                            amount });
            message.setStatus("200");
            eventPublisher.publishEvent(message);
        } catch (BankServiceException_Exception e) {
            Message message_report = new Message(
                    PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                    "ReportBroker",
                    new Object[] {
                            payType,
                            payOrRefundUuid,
                            creditorID,
                            debetorID,
                            amount });
            message_report.setStatus("404");

            Message message_facade = new Message(
                    PaymentConfig.SEND_PAYMENT_RESULT,
                    "PaymentFacadeBroker",
                    new Object[] {
                            payType,
                            payOrRefundUuid,
                            creditorBankAccount,
                            debetorBankAccount,
                            amount });
            message_facade.setStatus("404");

            try {
                eventPublisher.publishEvent(message_report);
                eventPublisher.publishEvent(message_facade);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            Log.error("Transfer failed");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        UUID customerUuid = paymentRepository.getPayment(paymentID).getCustomerId();
        eventPublisher.publishEvent(new Message(PaymentConfig.SEND_REQUEST_BANK_ACCOUNTS, "AccountBroker",
                new Object[] { refundId, merchantUuid, customerUuid, "refund" }));
    }

}
