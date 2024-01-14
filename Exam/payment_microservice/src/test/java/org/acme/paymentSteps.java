import dtu.ws.fastmoney.AccountInfo;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.acme.domains.Message;
import org.acme.domains.Payment;
import org.acme.resoures.EventPublisher;
import org.acme.resoures.EventSubscriber;
import org.acme.resoures.PaymentBroker;
import org.acme.resoures.PaymentConfig;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
//<dependency>
//        <groupId>org.mockito</groupId>
//        <artifactId>mockito-core</artifactId>
//        <version>5.8.0</version>
//        <scope>test</scope>
//        </dependency>

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class all_payment_test_steps {
    EventPublisher eventPublisher = mock(EventPublisher.class);
    EventSubscriber eventSubscriber = mock(EventSubscriber.class);
    PaymentBroker paymentBroker = new PaymentBroker();
    private Payment payment = new Payment();

    private Message message;
    private String MerchantBankID;
    private String customerBankID;
    private BigDecimal customerInitialBalance;
    private BigDecimal merchantInitialBalance;

    static String CustomerBankAccount;
    static String MerchantBankAccount;
    @Before
    public void before(){
        var user = new User();
        user.setCprNumber("TestCPRCustomer1-1");
        user.setFirstName("Test");
        user.setLastName("Test");

        var user2 = new User();
        user2.setCprNumber("TestCPRMerchant1-1");
        user2.setFirstName("Test");
        user2.setLastName("Test");

        var bank = new BankServiceService().getBankServicePort();

        List<AccountInfo> accounts = bank.getAccounts();
        for(int i = 0; i< accounts.size(); i++){
            AccountInfo info = accounts.get(i);
            if(
                    info.getUser().getCprNumber().equals(
                            user.getCprNumber()
                    )
                            ||
                            info.getUser().getCprNumber().equals(
                                    user2.getCprNumber()
                            )
            ){
                try {
                    bank.retireAccount(info.getAccountId());
                } catch (BankServiceException_Exception e) {
                    e.printStackTrace();
                }
            }
        }


        try {
            CustomerBankAccount = bank.createAccountWithBalance(user, new BigDecimal(1000));
            MerchantBankAccount = bank.createAccountWithBalance(user2, new BigDecimal(1000));
        } catch (BankServiceException_Exception e) {
            System.out.println("HERE: \n\n\n");
            e.printStackTrace();
            System.out.println("\n\n\n Done ");
        }
    }

    @AfterAll
    public static void afterAll() {
        var bank = new BankServiceService().getBankServicePort();

        try {
            bank.retireAccount(CustomerBankAccount);
            bank.retireAccount(MerchantBankAccount);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    }

    // Scenario: Successful Payment Transaction
    @Given("After getting the transaction information from the merchant with message ID {UUID}, merchant ID {UUID}, token {string}, and amount {BigDecimal}, publish the message event that validates the customer's token")
    public void afterGettingTransactionInfo(String messageID, String merchantID, String token, double amount) throws Exception {
//        UUID messageID = UUID.fromString(messageID);
//        UUID merchantID = UUID.fromString(merchantID);
//        BigDecimal amount = new BigDecimal(amount);
        message = new Message(PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT, "PaymentBroker" ,new Object[] {messageID, merchantID, token, amount});
        paymentBroker.subscribeEvent(message);
    }

    @Then("Validate customer's token message event is published")
    public void validateTokenMessagePublished2() throws Exception {
        verify(eventPublisher).publishEvent(message);
    }

    @When("After getting the validation result of the customer's token, post a message event to get the bank account numbers of both parties")
    public void afterGettingTokenValidationResult(UUID paymentID, boolean result) throws Exception {
        message = new Message(PaymentConfig.RECEIVE_VALID_RESULT, "PaymentBroker" ,new Object[] {paymentID, result});
        paymentBroker.subscribeEvent(message);
    }

    @Then("The message event to get the bank account numbers of both parties has been published")
    public void bankAccountNumbersMessagePublished() throws Exception {
        verify(eventPublisher).publishEvent(message);
    }

    @When("After obtaining the bank account numbers of both parties, the payment operation is performed and the message event updating the payment report is published")
    public void afterObtainingBankAccountNumbers(UUID paymentID, UUID merchantBankAccount, UUID customerBankAccount) throws Exception {
        message = new Message(PaymentConfig.RECEIVE_GET_ACCOUNTS, "PaymentBroker" ,new Object[] {paymentID, merchantBankAccount, customerBankAccount});
        paymentBroker.subscribeEvent(message);
    }

    @Then("The message event of updating the payment report is released, and the payment is successful")
    public void paymentReportUpdatedAndPaymentSuccessful() throws Exception {
        verify(eventPublisher).publishEvent(message);
    }

    // Scenario: Failed Payment Transaction Due to Invalid Token
    @Given("After getting the transaction information from the merchant, publish a message event validating the customer's token")
    public void afterGettingTransactionInfo2(UUID messageID, UUID merchantID, String token, BigDecimal amount) throws Exception {
        message = new Message(PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT, "PaymentBroker" ,new Object[] {messageID, merchantID, token, amount});
        paymentBroker.subscribeEvent(message);
    }

    @Then("A message event validating the customer's token is posted")
    public void validateTokenMessagePublished() throws Exception {
        verify(eventPublisher).publishEvent(message);
    }

    @When("After getting the validation result of the customer's token is invalid, publish the payment result message event")
    public void afterGettingInvalidTokenResult(UUID paymentID, boolean result, String reason) throws Exception {
        message = new Message(PaymentConfig.RECEIVE_VALID_RESULT, "PaymentBroker" ,new Object[] {paymentID, result, reason});
        paymentBroker.subscribeEvent(message);
    }

    @Then("The payment result message event has been posted, and the payment failed")
    public void paymentResultMessagePostedAndFailed() throws Exception {
        verify(eventPublisher).publishEvent(message);
    }
}
