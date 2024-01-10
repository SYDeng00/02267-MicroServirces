package payment;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import domin.DTUPayAccount;
import domin.DTUPay_Interface;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DTUPaySteps {

	public String customerBankID;
	public String customerDtuPayID;

	public String merchantBankID;
	public String merchantDtuPayID;

	public DTUPayAccount dtu_customer;
	public DTUPayAccount dtu_merchant;

	
	public List<String> tokens = new ArrayList<>();;
	
	
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
		assertFalse(customerDtuPayID.isEmpty());
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
		assertFalse(merchantDtuPayID.isEmpty());
	}

	@After
	public void resetUsers() throws BankServiceException_Exception {
		if (customerBankID != null)
			bank.retireAccount(customerBankID);
		if (merchantBankID != null)
			bank.retireAccount(merchantBankID);
	}

	// Token test
	// Author: Siyuan Deng

	@When("the customer asks for tokens")
	public void theCustomerAsksForTokens() {
		try {
			tokens = dtuPay.getTokens(customerDtuPayID);
		} catch (Exception e) {
		    // Handle the exception, e.g., log it
		    e.printStackTrace();
		}
		

	}

	@Then("the customer receives tokens")
	public void theCustomerReceivesTokens() {
		assertFalse(tokens.isEmpty());
	}

}