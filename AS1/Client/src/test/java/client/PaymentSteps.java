package client;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PaymentSteps {
	public String customer_Account;
	public Integer customer_Balance;
	public String merchant_Account;
	public int merchant_Balance;
	public int balance;
	public ClientService clientService = new ClientService();

	@Given("a customer with a bank account with balance {int}")
	public void aCustomerWithABankAccountWithBalance(Integer int1) {
    	this.customer_Account = "12ec8efc-f3db-43b0-83d5-9a4248dac88b";
    	this.customer_Balance = int1;
	}

	@Given("that the customer is registered with DTU Pay")
	public void thatTheCustomerIsRegisteredWithDTUPay() {

	}

	@Given("a merchant with a bank account with balance {int}")
	public void aMerchantWithABankAccountWithBalance(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		this.merchant_Account = "690fd7eb-1edd-4600-961c-23685795f2ff";
		merchant_Balance = int1;
	}

	@Given("that the merchant is registered with DTU Pay")
	public void thatTheMerchantIsRegisteredWithDTUPay() {
	    // Write code here that turns the phrase above into concrete actions

	}

	@When("the merchant initiates a payment for {int} kr by the customer")
	public void theMerchantInitiatesAPaymentForKrByTheCustomer(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    balance = int1;
		String jsonData = "[{\"customerBankAccount\":\"" + this.customer_Account +
                "\",\"merchantBankAccount\":\"" + this.merchant_Account +
                "\",\"balance\":" + balance +
                ",\"customerBalance\":" + this.customer_Balance +
                ",\"merchantBalance\":" + this.merchant_Balance + "}]";

//		JsonParser parser = new JsonParser();
//
//		JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();
		//String rt = clientService.transferMoney(Entity.entity(jsonData, MediaType.APPLICATION_JSON_TYPE));
		String rt = clientService.transferMoney(jsonData);
		// Replace "name" with the key you want to find
		System.out.println(rt);

//
		String mb= rt.get("merchantBalance").getAsString();
		String cb= rt.get("customerBalance").getAsString();
//
//		assertEquals(merchant_Balance + balance,mb);
//		assertEquals(customer_Balance-balance,cb);
	}

	@Then("the payment is successful")
	public void thePaymentIsSuccessful() {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("the balance of the customer at the bank is {int} kr")
	public void theBalanceOfTheCustomerAtTheBankIsKr(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("the balance of the merchant at the bank is {int} kr")
	public void theBalanceOfTheMerchantAtTheBankIsKr(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	}
}

