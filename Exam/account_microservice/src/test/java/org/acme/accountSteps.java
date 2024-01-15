package org.acme;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.acme.Domains.Message;
import org.acme.Resoures.EventPublisher;

import Resources.AccountBroker;
import Resources.AccountConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class accountSteps {

	AccountBroker accountBroker = new AccountBroker();
	public Message expectedMessage = mock(Message.class);

	@Given("a message event {string} from payment service")
	public void a_message_event_from_payment_service(String string) throws Exception {
		UUID paymentID = UUID.randomUUID();
		UUID merchantBankAccount = UUID.randomUUID();
		UUID customerBankAccount = UUID.randomUUID();
		String paymentType = "payment";

		// Create a Message with the expected payload
		expectedMessage = new Message(AccountConfig.SEND_REQUEST_BANK_ACCOUNTS, "AccountBroker",
				new Object[] { paymentID, merchantBankAccount, customerBankAccount, paymentType });

	}

	@Then("return the BankID")
	public void return_the_bank_id() throws Exception {
		accountBroker.subscribeEvent(expectedMessage);
	}
}