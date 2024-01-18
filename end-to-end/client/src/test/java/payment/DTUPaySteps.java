package payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Domains.DTUPayAccount;
import Domains.DTUPay_Interface;
import Domains.Payment;
import Domains.Token_client;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

public class DTUPaySteps {

	public String customerBankID;
	public UUID customerDtuPayID;
UUID invalidToken;
	public String merchantBankID;
	public UUID merchantDtuPayID;

	public DTUPayAccount dtu_customer;
	public DTUPayAccount dtu_merchant;
	Token_client request_token_client;
	public Token_client token_client = new Token_client();

	public List<UUID> tokens = new ArrayList<>();;


	public BigDecimal payment_amount;
	public String payment_result;

	DTUPay_Interface dtuPay = new DTUPay_Interface();
	BankService bank = new BankServiceService().getBankServicePort();



	// Account test
	// Author: Siyuan Deng

	@Given("the customer {string} {string} with CPR {string} with balance {int}")
	public void the_customer_with_cpr_with_balance(String firstName, String lastName, String cpr, Integer balance)
			throws BankServiceException_Exception {
		User bank_user_customer = new User();
		bank_user_customer.setFirstName(firstName);
		bank_user_customer.setLastName(lastName);
		bank_user_customer.setCprNumber(cpr);
		BigDecimal customerBalance = new BigDecimal(balance);
		customerBankID = bank.createAccountWithBalance(bank_user_customer, customerBalance);
		assertFalse(customerBankID.isEmpty());

		dtu_customer = new DTUPayAccount(firstName, lastName, cpr, customerBankID);
	}

	@When("the customer has registered with DTUPay")
	public void the_customer_has_registered_with_dtu_pay() {
		customerDtuPayID = dtuPay.createDTUPayAccount(dtu_customer);
	}

	@Then("we receive a customer dtuPayId")
	public void we_receive_a_dtu_pay_id() {
		assertNotNull(customerDtuPayID);
	}

	@Given("the merchant {string} {string} with CPR {string} with balance {int}")
	public void the_merchant_with_cpr_with_balance(String firstName, String lastName, String cpr, Integer balance)
			throws BankServiceException_Exception {
		User bank_user_merchant = new User();
		bank_user_merchant.setFirstName(firstName);
		bank_user_merchant.setLastName(lastName);
		bank_user_merchant.setCprNumber(cpr);
		BigDecimal merchantBalance = new BigDecimal(balance);
		merchantBankID = bank.createAccountWithBalance(bank_user_merchant, merchantBalance);
		assertFalse(merchantBankID.isEmpty());
		dtu_merchant = new DTUPayAccount(firstName, lastName, cpr, customerBankID);
	}

	@When("the merchant has registered with DTUPay")
	public void the_merchant_has_registered_with_dtu_pay() {
		merchantDtuPayID = dtuPay.createDTUPayAccount(dtu_merchant);
	}

	@Then("we receive a merchant dtuPayId")
	public void we_receive_a_merchant_dtu_pay_id() throws BankServiceException_Exception {
		assertNotNull(merchantDtuPayID);
	}



	// Token test
	// Author: Siyuan Deng
	@When("the customer asks for {int} tokens")
	public void theCustomerAsksForTokens(Integer int1) {
		token_client.setCustomerID(customerDtuPayID);
		token_client.setToken_number(int1);
		try {
			tokens = dtuPay.getTokens(token_client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	@Then("the customer receives tokens")
	public void theCustomerReceivesTokens() {
		if(tokens==null){
			fail();
		}
		else{
			assertFalse(tokens.isEmpty());
		}
	}

	// Payment test
	// Author: Siyuan Deng

	@Given("the merchant initiates a payment for {int} kr by the customer")
	public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
		payment_amount = new BigDecimal(amount);

	}

	@When("the merchant has received a token from the customer")
	public void theMerchantHasReceivedATokenFromTheCustomer() {
		UUID token = null;
		if(tokens==null){
			fail();
		}else {

			token = tokens.get(0);


			Payment payment = new Payment(merchantDtuPayID, token, payment_amount);
			payment_result = dtuPay.createPayment(payment);
		}
	}

	@Then("the payment is successful")
	public void thePaymentIsSuccessful() {
		assertEquals("payment is successful",payment_result);
	}

	@Then("the payment is Failed")
	public void thePaymentIsFailed() throws BankServiceException_Exception {
		BigDecimal merchantBalance = bank.getAccount(merchantBankID).getBalance();
		BigDecimal customerBalance = bank.getAccount(customerBankID).getBalance();
		assertEquals(BigDecimal.valueOf(1000), merchantBalance);
		assertEquals(BigDecimal.valueOf(1000),customerBalance);
		assertEquals("payment fail",payment_result);
	}

	@When("the merchant has received a invalid from the customer")
	public void theMerchantHasReceivedAInvalidFromTheCustomer() {
		invalidToken = UUID.randomUUID();

	}

	@After
	public void resetUsers() throws BankServiceException_Exception {
		if (customerBankID != null)
			bank.retireAccount(customerBankID);
		if (merchantBankID != null)
			bank.retireAccount(merchantBankID);
	}

//	@When("the customer asks for {int} tokens")
//	public void theCustomerAsksForIntTokens(int token_num) {
//		try {
//			token_client.setCustomerID(customerDtuPayID);
//			token_client.setToken_number(token_num);
//			tokens = dtuPay.getTokens(token_client);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	@When("the customer asks for {int} tokens again")
	public void theCustomerAsksForTokensAgain(int token_num) {
		try {
			token_client.setCustomerID(customerDtuPayID);
			token_client.setToken_number(token_num);
			tokens = dtuPay.getTokens(token_client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Then("the request for more tokens failed")
	public void theRequestForMoreTokensFailed() {
		if(tokens==null){
			assertTrue(true);
		}else{
			fail();
		}
	}
}