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
import org.acme.Domains.Payment;
import org.acme.Domains.Refund;
import org.acme.Repositories.PaymentRepository;
import org.acme.Resources.PaymentHandler;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class paymentSteps {
    UUID merchantID;
    UUID token;
    BigDecimal affordedAmount;
    BigDecimal unAffordedAmount;

    String merchantCpr = "001";
    String customerCpr = "002";
    String merchantFirstName = "mfn1";
    String customerFirstName = "cfn1";
    String merchantLastName = "mln1";
    String customerLastName = "cln1";
    String merchantBankAccount;
    String customerBankAccount;
    UUID paymentID;
    UUID customerID;

    PaymentHandler paymentHandler = new PaymentHandler();
    BankService bank = new BankServiceService().getBankServicePort();
    PaymentRepository paymentRepository = PaymentRepository.getInstance();

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
    public void merchant_id_token() {
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
            //e.printStackTrace();
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
        payment.setCustomerID(customerID);
        paymentRepository.addPayment(payment);
        paymentRepository.getPayment(paymentID).getPaymentID();
        //refund
        refundId = UUID.randomUUID();
        paymentRepository.addRefund(new Refund(refundId, paymentID, merchantID, affordedAmount));
    }

    @Then("Ask bank for refund transaction")
    public void Ask_bank_for_refund_transaction() throws Exception {
        paymentHandler.getBankAccount(new Object[]{refundId,merchantBankAccount,customerBankAccount,"refund"});
    }

    @Then("the refund transaction succeed")
    public void theRefundTransactionSucceed() throws BankServiceException_Exception {
        BigDecimal merchantBalance = bank.getAccount(merchantBankAccount).getBalance();
        BigDecimal customerBalance = bank.getAccount(customerBankAccount).getBalance();
        assertEquals(BigDecimal.valueOf(900), merchantBalance);
        assertEquals(BigDecimal.valueOf(1100), customerBalance);
    }
    Boolean tokenValidationResult;

    @When("Received token validation result {string}")
public void received_token_validation_result_result(String result) {
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
