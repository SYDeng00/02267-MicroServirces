package org.acme.Resoures;

public class PaymentConfig {
    public static final String RECEIVE_MERCHANT_ASK_PAYMENT = "MerchantAskPayment";
    //{UUID->merchantID, String->token, BigDecimal->amount}

    public static final String SEND_VALID_TOKENS = "ValidTokens";
    //{UUID->paymentID, String-> token}

    public static final String RECEIVE_VALID_RESULT = "ValidResult";
    //{UUID->payment, boolean->result}

    public static final String SEND_REQUEST_BANK_ACCOUNTS = "RequestBankAccounts";
    //{UUID->paymentID, UUID->merchantID, UUID->customerID}

    public static final String RECEIVE_GET_ACCOUNTS = "GET_ACCOUNTS";
    //{UUID->paymentID, UUID->merchantBankAccount, UUI->customerBankAccount}

    public static final String SEND_UPDATE_PAYMENTS_REPORT = "UpdatePaymentsReport";
    //{UUID->paymentID, UUID->merchantID, UUID->customerID, BigDecimal->amount}
}
