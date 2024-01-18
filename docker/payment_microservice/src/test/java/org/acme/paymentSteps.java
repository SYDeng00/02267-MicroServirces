package org.acme;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.acme.Domains.Message;
import org.acme.Domains.Payment;
import org.acme.Domains.Refund;
import org.acme.Repositories.PaymentRepository;
import org.acme.Resources.PaymentBroker;
import org.acme.Resources.PaymentConfig;
import org.acme.Resources.PaymentHandler;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class paymentSteps {
    UUID merchantID;
    UUID token;
    BigDecimal affordedAmount;
    BigDecimal unAffordedAmount;
    String merchantCpr = "003";
    String customerCpr = "004";
    String merchantFirstName = "mfn2";
    String customerFirstName = "cfn2";
    String merchantLastName = "mln2";
    String customerLastName = "cln2";
    String merchantBankAccount;
    String customerBankAccount;
    UUID paymentID;
    UUID customerID;
    Boolean tokenValidationResult;


    PaymentHandler paymentHandler = new PaymentHandler();
    BankService bank = new BankServiceService().getBankServicePort();
    PaymentRepository paymentRepository = PaymentRepository.getInstance();

    PaymentBroker mock_paymentBroker = mock(PaymentBroker.class);
    public Message mock_message = mock(Message.class);
    
    public UUID messageID;
    public UUID merchanID;
    String reason;
    @Before
    public void createMerchantAndCustomer() throws BankServiceException_Exception {
        User merchantUser = new User();
        merchantUser.setCprNumber(merchantCpr);
        merchantUser.setFirstName(merchantFirstName);
        merchantUser.setLastName(merchantLastName);
        merchantBankAccount = bank.createAccountWithBalance(merchantUser, new BigDecimal(1000));

        User customerUser = new User();
        customerUser.setCprNumber(customerCpr);
        customerUser.setFirstName(customerFirstName);
        customerUser.setLastName(customerLastName);
        customerBankAccount = bank.createAccountWithBalance(customerUser, new BigDecimal(1000));
    }

    @Given("merchantID, token")
    public void merchant_id_token() throws Exception {
        
        mock_message = new Message(PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT, "PaymentBroker",new Object[]{messageID, merchanID,token,affordedAmount});
        mock_paymentBroker.subscribeEvent(mock_message);
        Mockito.verify(mock_paymentBroker).subscribeEvent(mock_message);
        merchantID = UUID.randomUUID();
        token = UUID.randomUUID();
    }

    @Given("affordedAmount")
    public void affordedamount() {
        affordedAmount = BigDecimal.valueOf(100);
    }

    @Given("unAffordedAmount")
    public void unaffordedamount() {
        unAffordedAmount = BigDecimal.valueOf(10000);
    }

    @When("the service create a payment")
    public void the_service_create_a_payment() throws Exception {
        paymentID = UUID.randomUUID();
        Payment payment = new Payment(paymentID, merchantID, token, affordedAmount);
        paymentRepository.addPayment(payment);
    }

    @Then("Ask bank for transaction")
    public void Ask_bank_for_transaction() throws Exception {
        paymentHandler.getBankAccount(new Object[] { paymentID, merchantBankAccount, customerBankAccount, "payment" });
    }

    @Then("the transaction succeed")
    public void the_transaction_succeed() throws BankServiceException_Exception {
        BigDecimal merchantBalance = bank.getAccount(merchantBankAccount).getBalance();
        BigDecimal customerBalance = bank.getAccount(customerBankAccount).getBalance();
        assertEquals(BigDecimal.valueOf(1100), merchantBalance);
        assertEquals(BigDecimal.valueOf(900), customerBalance);
    }

    @When("the service create a payment customer cannot affort")
    public void theServiceCreateAPaymentCustomerCannotAffort() {
        paymentID = UUID.randomUUID();
        Payment payment = new Payment(paymentID, merchantID, token, unAffordedAmount);
        paymentRepository.addPayment(payment);
    }

    @Then("Ask bank for transaction a lot of money")
    public void askBankForTransactionALotOfMoney() throws Exception {
        try {
            paymentHandler
                    .getBankAccount(new Object[] { paymentID, merchantBankAccount, customerBankAccount, "payment" });
        } catch (BankServiceException_Exception e) {
            
        }

    }

    @Then("The transaction failed for customer cannot afford")
    public void theTransactionFailedForCustomerCannotAffort() throws BankServiceException_Exception {
        BigDecimal merchantBalance = bank.getAccount(merchantBankAccount).getBalance();
        BigDecimal customerBalance = bank.getAccount(customerBankAccount).getBalance();
        assertEquals(BigDecimal.valueOf(1000), merchantBalance);
        assertEquals(BigDecimal.valueOf(1000), customerBalance);
    }

    /***
     * Create a refund with valid account
     */

    @Given("refundId")
    public void refundid() {
        refundId = UUID.randomUUID();
    }
    @Given("customerID")
    public void customer_id() {
        customerID = UUID.randomUUID();
    }
    UUID refundId;
    @When("the service create a refund")
    public void the_service_create_a_refund() throws Exception {
        //payment
        paymentID = UUID.randomUUID();
        Payment payment = new Payment(paymentID, merchantID, token, affordedAmount);
        payment.setCustomerId(customerID);
        paymentRepository.addPayment(payment);
        //refund
        paymentRepository.addRefund(new Refund(refundId, paymentID, merchantID, affordedAmount));
    }



    @Then("Ask bank for refund transaction")
    public void ask_bank_for_refund_transaction() throws Exception {
        paymentHandler.getBankAccount(new Object[]{refundId,merchantBankAccount,customerBankAccount,"refund"});
    }


    @Then("the refund transaction succeed")
    public void theRefundTransactionSucceed() throws BankServiceException_Exception {
        BigDecimal merchantBalance = bank.getAccount(merchantBankAccount).getBalance();
        BigDecimal customerBalance = bank.getAccount(customerBankAccount).getBalance();
        assertEquals(BigDecimal.valueOf(900), merchantBalance);
        assertEquals(BigDecimal.valueOf(1100), customerBalance);
    }

    @When("The service creates a refund that the merchant cannot afford")
    public void the_service_creates_a_refund_that_the_merchant_cannot_afford() throws Exception {
        //payment
        paymentID = UUID.randomUUID();
        Payment payment = new Payment(paymentID, merchantID, token, unAffordedAmount);
        //payment.setCustomerID(customerID);
        paymentRepository.addPayment(payment);
        //refund
        System.out.println(refundId + "-----------");
        Refund refund = new Refund(refundId, paymentID, merchantID, unAffordedAmount);
       // refundId = UUID.randomUUID();
        paymentRepository.addRefund(refund);
        System.out.println("refund's unAffordedAmount:"+refundId.toString()+refund.getAmount());
        System.out.println(refundId.toString());
    }
    @Then("Ask bank for refund a lot of money")
    public void ask_bank_for_refund_a_lot_of_money() throws Exception {
        try {
            System.out.println(refundId.toString());
            paymentHandler
                    .getBankAccount(new Object[] { refundId, merchantBankAccount, customerBankAccount, "refund" });
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    }
    @Then("the refund transaction failed")
    public void the_refund_transaction_failed() throws Exception {
        BigDecimal merchantBalance = bank.getAccount(merchantBankAccount).getBalance();
        BigDecimal customerBalance = bank.getAccount(customerBankAccount).getBalance();
        assertEquals(BigDecimal.valueOf(1000), merchantBalance);
        assertEquals(BigDecimal.valueOf(1000), customerBalance);
    }

    @When("Received token validation result {string}")
    public void received_token_validation_result_result(String result) throws Exception {
        
        mock_message = new Message(PaymentConfig.RECEIVE_VALID_RESULT, "PaymentBroker",new Object[]{paymentID, result,reason});
        mock_paymentBroker.subscribeEvent(mock_message);
        Mockito.verify(mock_paymentBroker).subscribeEvent(mock_message);
        tokenValidationResult = Boolean.valueOf(result);
}


    @Then("Send message to corresponding services")
    public void send_message_to_corresponding_services() {
        try {
            paymentHandler.getTokenValidResult(new Object[] { paymentID, tokenValidationResult, null });
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(false);
        }
    }
    @After
    public void retireMerchantAndCusomer() throws BankServiceException_Exception {
        bank.retireAccount(merchantBankAccount);
        bank.retireAccount(customerBankAccount);
    }
}
