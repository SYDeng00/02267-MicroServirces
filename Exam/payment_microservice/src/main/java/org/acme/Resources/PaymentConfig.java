package org.acme.Resources;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class PaymentConfig {
    private PaymentConfig() {
        throw new UnsupportedOperationException("This class should not be instantiated.");
    }

    public static final String RECEIVE_MERCHANT_ASK_PAYMENT = "MerchantAskPayment";
    // {UUID-> messageID, UUID->merchantID, String->token, BigDecimal->amount}

    public static final String SEND_VALID_TOKENS = "ValidTokens";
    // {UUID->paymentID, UUID-> token}

    public static final String RECEIVE_VALID_RESULT = "ValidResult";
    // {UUID->paymentID, boolean->result, String->reason, UUID customerID} if token is valid, reason
    // is null

    public static final String SEND_PAYMENT_RESULT = "PaymentResult";
    // {UUID->paymmentID, UUID+:messageId,String->reason}

    public static final String SEND_REQUEST_BANK_ACCOUNTS = "RequestBankAccounts";
    // {UUID->paymentID, UUID->merchantID, UUID->customerID, String->payment
    // type(payment/refund)}

    public static final String RECEIVE_GET_ACCOUNTS = "RequestBankAccounts";
    // {UUID->paymentID, UUID->merchantBankAccount, UUI->customerBankAccount}

    public static final String SEND_UPDATE_PAYMENTS_REPORT = "UpdatePaymentsReport";

    // {String->paymentType(payment/refund), UUID->paymentID, UUID->merchantID, 
    // UUID->customerID, BigDecimal->amount,UUID token}

    public static final String RECEIVE_REFUND_REQUEST = "MerchantAskRefund";
    // {UUID->paymentID, UUID-> messageID, UUID->merchantID, BigDecimal->amount}

}
