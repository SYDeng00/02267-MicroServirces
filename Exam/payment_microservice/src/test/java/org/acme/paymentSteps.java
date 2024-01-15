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
import org.acme.Repositories.PaymentRepository;
import org.acme.Resources.PaymentHandler;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class paymentSteps {
    UUID merchantID;
    UUID token;
    BigDecimal amount;

    String merchantCpr = "001";
    String customerCpr = "002";
    String merchantFirstName = "mfn1";
    String customerFirstName = "cfn1";
    String merchantLastName = "mln1";
    String customerLastName = "cln1";
    String merchantBankAccount;
    String customerBankAccount;
    UUID paymentID;
PaymentHandler paymentHandler = new PaymentHandler();
    BankService bank = new BankServiceService().getBankServicePort();
    PaymentRepository paymentRepository =  PaymentRepository.getInstance();
    @Before
    public void createMerchantAndCustomer() throws BankServiceException_Exception {

        User merchantUser = new User();
        merchantUser.setCprNumber(merchantCpr);
        merchantUser.setFirstName(merchantFirstName);
        merchantUser.setLastName(merchantLastName);
        merchantBankAccount = bank.createAccountWithBalance(merchantUser,new BigDecimal(1000));

        User customerUser = new User();
        customerUser.setCprNumber(customerCpr);
        customerUser.setFirstName(customerFirstName);
        customerUser.setLastName(customerLastName);
        customerBankAccount = bank.createAccountWithBalance(customerUser,new BigDecimal(1000));
    }
    @Given("merchantID, token, amount")
    public void merchant_id_token_amount() {
        merchantID = UUID.randomUUID();
        token = UUID.randomUUID();
        amount = BigDecimal.valueOf(100);
    }

    @When("the service create a payment")
    public void the_service_create_a_payment() throws Exception {
        paymentID = UUID.randomUUID();
        Payment payment = new Payment(paymentID, merchantID, token, amount);
        paymentRepository.addPayment(payment);

        //UUID->paymentID, UUID->merchantBankAccount, UUI->customerBankAccount
    }

    @Then("Ask bank for transaction")
    public void Ask_bank_for_transaction() throws Exception {
        paymentHandler.getBankAccount(new Object[]{paymentID,merchantBankAccount,customerBankAccount,"payment"});
    }
    @Then("the transaction succeed")
    public void the_transaction_succeed() throws BankServiceException_Exception {
        BigDecimal merchantBalance = bank.getAccount(merchantBankAccount).getBalance();
        BigDecimal customerBalance =  bank.getAccount(customerBankAccount).getBalance();
        assertEquals(BigDecimal.valueOf(1100), merchantBalance);
        assertEquals(BigDecimal.valueOf(900),customerBalance);
    }


@After
public void retireMerchantAndCusomer() throws BankServiceException_Exception {
        bank.retireAccount(merchantBankAccount);
        bank.retireAccount(customerBankAccount);
}
//
//    @Given("merchantID, token, amount")
//    public void invalid_merchant_id_token_amount() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//    @When("the service ask for authentication")
//    public void the_service_ask_for_authentication() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//    @Then("The token is invalid")
//    public void the_token_is_invalid() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }


}
