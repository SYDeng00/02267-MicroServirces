package payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Adapter.DTUPay_Interface;
import Domains.DTUPayAccount;
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

import static org.junit.Assert.*;

/*
Author: Siyuan Deng s232275   Yingli Duan s222462
*/
public class DTUPaySteps {

	public String customerBankID;
	public UUID customerDtuPayID;

	public String merchantBankID;
	public UUID merchantDtuPayID;

	public DTUPayAccount dtu_customer;
	public DTUPayAccount dtu_merchant;

	public Token_client token_client = new Token_client();

	public List<UUID> tokens = new ArrayList<>();;

	public BigDecimal payment_amount;
	public String payment_result;
	
	BigDecimal customerBalance;
	BigDecimal merchantBalance;
	DTUPay_Interface dtuPay = new DTUPay_Interface();
	BankService bank = new BankServiceService().getBankServicePort();
	private UUID token;

	// Account test

	@Given("the customer {string} {string} with CPR {string} with balance {int}")
	public void the_customer_with_cpr_with_balance(String firstName, String lastName, String cpr, Integer balance)
			throws BankServiceException_Exception {
		User bank_user_customer = new User();
		bank_user_customer.setFirstName(firstName);
		bank_user_customer.setLastName(lastName);
		bank_user_customer.setCprNumber(cpr);
		customerBalance = new BigDecimal(balance);
		customerBankID = bank.createAccountWithBalance(bank_user_customer, customerBalance);
		System.out.println(customerBankID);
		assertFalse(customerBankID.isEmpty());

		dtu_customer = new DTUPayAccount(firstName, lastName, cpr, customerBankID);
	}

	@When("the customer has registered with DTUPay")
	public void the_customer_has_registered_with_dtu_pay() throws Exception {
		customerDtuPayID = dtuPay.create_customerDTUPayAccount(dtu_customer);
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
		merchantBalance = new BigDecimal(balance);
		merchantBankID = bank.createAccountWithBalance(bank_user_merchant, merchantBalance);
		System.out.println(merchantBankID);
		assertFalse(merchantBankID.isEmpty());
		dtu_merchant = new DTUPayAccount(firstName, lastName, cpr, merchantBankID);
	}

	@When("the merchant has registered with DTUPay")
	public void the_merchant_has_registered_with_dtu_pay() throws Exception {
		merchantDtuPayID = dtuPay.create_merchantDTUPayAccount(dtu_merchant);
	}

	@Then("we receive a merchant dtuPayId")
	public void we_receive_a_merchant_dtu_pay_id() throws BankServiceException_Exception {
		assertFalse(merchantDtuPayID == null);
	}

	// Token test
	
	@When("the customer asks for {int} tokens")
	public void theCustomerAsksForTokens(Integer int1) {
		token_client.setCustomerID(customerDtuPayID);
		token_client.setToken_number(int1);

		try {
			System.out.println(token_client.getCustomerID());
			System.out.println(token_client.getToken_number());
			tokens = dtuPay.getTokens(token_client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	@When("the merchant has received an invalid from the customer")
	public void theMerchantHasReceivedAnInvalidFromTheCustomer() {
		// Write code here that turns the phrase above into concrete actions
		token = UUID.randomUUID();
	}

	@Then("the customer receives tokens")
	public void theCustomerReceivesTokens() {
		assertFalse(tokens.isEmpty());
	}

	// Payment test

	@Given("the merchant initiates a payment for {int} kr by the customer")
	public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
		payment_amount = new BigDecimal(amount);

	}

	@When("the merchant has received a token from the customer")
	public void theMerchantHasReceivedATokenFromTheCustomer() {
		token = null;
		if (!tokens.isEmpty()) {
			token = tokens.get(0);
		}

		Payment payment = new Payment(merchantDtuPayID, token, payment_amount);
		payment_result = dtuPay.createPayment(payment);
	}

	@Then("the payment is successful")
	public void thePaymentIsSuccessful() {
		BigDecimal customerBalance_now = BigDecimal.valueOf(2);
		BigDecimal merchanBalance_now = BigDecimal.valueOf(2);
		try {
			merchanBalance_now = bank.getAccount(merchantBankID.toString()).getBalance();
			customerBalance_now = bank.getAccount(customerBankID.toString()).getBalance();
		} catch (BankServiceException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("payment is successful", payment_result);
		assertEquals(customerBalance.intValue() - payment_amount.intValue(), customerBalance_now.intValue());
		assertEquals(merchantBalance.intValue() +  payment_amount.intValue(), merchanBalance_now.intValue());



	}

	@Then("the payment is Failed")
	public void thePaymentIsFailed() {
		BigDecimal merchanBalance_now = BigDecimal.valueOf(0);
		BigDecimal customerBalance_now = BigDecimal.valueOf(0);
		try {
			merchanBalance_now = bank.getAccount(merchantBankID.toString()).getBalance();
			customerBalance_now = bank.getAccount(customerBankID.toString()).getBalance();
		} catch (BankServiceException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(customerBalance.intValue(), merchanBalance_now.intValue());

		assertEquals(merchantBalance.intValue(), customerBalance_now.intValue());
		assertNotEquals("payment is successful", payment_result);

	}

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
		if (tokens == null) {
			assertTrue(true);
		} else {
			fail();
		}
	}


	@After
	public void resetUsers() throws BankServiceException_Exception {
		if (customerBankID != null)
			bank.retireAccount(customerBankID);
		if (merchantBankID != null)
			bank.retireAccount(merchantBankID);
	}

}