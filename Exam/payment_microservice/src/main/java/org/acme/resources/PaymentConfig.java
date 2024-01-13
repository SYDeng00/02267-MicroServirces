package org.acme.resources;

public class PaymentConfig {
    private PaymentConfig() {
        throw new UnsupportedOperationException("This class should not be instantiated.");
    }
    //{UUID->merchantID, String->token, BigDecimal->amount}
    public static final String RECEIVE_MERCHANT_ASK_PAYMENT = "MerchantAskPayment";
    

    public static final String SEND_VALID_TOKENS = "ValidTokens"; //{UUID->paymentID, String-> token}

    public static final String RECEIVE_VALID_RESULT = "ValidResult";
    //{UUID->paymentID, boolean->result, String->reason} if token is valid, reason is null

    public static final String SEND_TO_MERCHANT_TOKEN_INVALID = "TokenInvalid";
    // {UUID->paymmentID, String->reason}

    public static final String SEND_REQUEST_BANK_ACCOUNTS = "RequestBankAccounts";
    //{UUID->paymentID, UUID->merchantID, UUID->customerID, String->payment type(payment/refund)}

    public static final String RECEIVE_GET_ACCOUNTS = "GET_ACCOUNTS";
    //{UUID->paymentID, UUID->merchantBankAccount, UUI->customerBankAccount}

    public static final String SEND_UPDATE_PAYMENTS_REPORT = "UpdatePaymentsReport";

    //{String->paymentType(payment/refund), UUID->paymentID, UUID->merchantID, UUID->customerID, BigDecimal->amount}


    public static final String RECEIVE_REFUND_REQUEST = "MerchantAskRefund";
    //{UUID->paymentID, UUID->merchantID, BigDecimal->amount}
}
