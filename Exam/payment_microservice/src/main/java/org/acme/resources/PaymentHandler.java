package org.acme.resources;

import java.math.BigDecimal;
import java.util.UUID;
import org.jboss.logging.Logger;

import org.acme.domains.Payment;
import org.acme.domains.Refund;
import org.acme.Domains.Message;
import org.acme.Repositories.PaymentRepository;
import org.acme.Resoures.EventPublisher;

import com.google.gson.Gson;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import io.quarkus.logging.Log;

public class PaymentHandler {
    EventPublisher eventPublisher = new EventPublisher();

    PaymentRepository paymentRepository = new PaymentRepository();
    private static final Logger LOG = Logger.getLogger(PaymentHandler.class);

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    public void getPayment(Object[] payload) throws Exception {
        UUID paymentID = UUID.randomUUID();
        UUID merchanUuid = typeTransfer(payload[0], UUID.class);
        String token = typeTransfer(payload[1], String.class);
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
            eventPublisher.publishEvent(new Message(PaymentConfig.SEND_TO_MERCHANT_TOKEN_INVALID, "PaymentFacadeBroker",
                    new Object[] { paymentID, merchaneUuid, reason }));
            LOG.error("Token validation failed:" + reason);
        }
    }

    public void getBankAccount(Object[] payload) {
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

            eventPublisher.publishEvent(
                    new Message(
                            PaymentConfig.SEND_UPDATE_PAYMENTS_REPORT,
                            "ReportBroker",
                            new Object[] {
                                    payType,
                                    payOrRefundUuid,
                                    creditorBankAccount,
                                    debetorBankAccount,
                                    amount }));
        } catch (Exception e) {
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
