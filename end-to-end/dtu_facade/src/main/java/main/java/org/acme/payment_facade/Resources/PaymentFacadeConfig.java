package main.java.org.acme.payment_facade.Resources;
/**
 * 
 * @author Yingli
 * @version 1.0
 * 
 */
public class PaymentFacadeConfig {
    public static final String RECEIVE_MERCHANT_ASK_PAYMENT = "MerchantAskPayment";
    // {UUID->merchantID, String->token, BigDecimal->amount}

    public static final String RECEIVE_PAYMENT_RESULT = "PaymentResult";
    // {UUID->paymmentID, UUID->smessageId,String->reason}

    public static final String RECEIVE_REFUND_REQUEST = "MerchantAskRefund";
    // {UUID->paymentID, UUID->merchantID, BigDecimal->amount}
}
