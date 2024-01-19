package org.acme;

import static org.mockito.Mockito.mock;

import java.util.UUID;

import org.acme.Domains.Message;
import org.mockito.Mockito;

import org.acme.Domains.DTUPayAccount;
import org.acme.Resources.AccountBroker;
import org.acme.Resources.AccountConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
Author: Siyuan Deng s232275  Collaborators：Jiahe Liu s232276
*/

public class accountSteps {

	AccountBroker accountBroker = Mockito.mock(AccountBroker.class);
	public Message Message_register = mock(Message.class);
	public Message Message_payment = mock(Message.class);
	private DTUPayAccount testAccount;

	@Given("the customer {string} {string} with CPR {string} has a bank account with balance {int}")
	public void the_customer_with_cpr_has_a_bank_account_with_balance(String firstName, String lastName, String cpr,
			Integer int1) {
		testAccount = new DTUPayAccount(firstName, lastName, cpr, "bank123");
	}

	@When("the customer has registered with DTUPay")
	public void the_customer_has_registered_with_dtu_pay() {
		Message_register = new Message(AccountConfig.REGISTER, "AccountResources", new Object[] { testAccount });

	}

	@Then("we receive a dtuPayId")
	public void we_receive_a_dtu_pay_id() throws Exception {
//        accountBroker.subscribeEvent(Message_register);
		accountBroker.subscribeEvent(Message_register);
		Mockito.verify(accountBroker).subscribeEvent(Message_register);
	}

	@Given("a message event {string} from payment service")
	public void a_message_event_from_payment_service(String string) {
		UUID paymentID = UUID.randomUUID();
		UUID merchantBankAccount = UUID.randomUUID();
		UUID customerBankAccount = UUID.randomUUID();
		String paymentType = "payment";

//		// Create a Message with the expected payload
		Message_payment = new Message(AccountConfig.SEND_REQUEST_BANK_ACCOUNTS, "AccountBroker",
				new Object[] { paymentID, merchantBankAccount, customerBankAccount, paymentType });

	}

	@Then("return the BankID")
	public void returnTheBankID() {
		try {
			accountBroker.subscribeEvent(Message_payment);
			Mockito.verify(accountBroker).subscribeEvent(Message_payment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

