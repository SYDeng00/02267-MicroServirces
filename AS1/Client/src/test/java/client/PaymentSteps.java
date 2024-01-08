package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.acme.Controllers.GeneralController;
import org.acme.Models.Customer;
import org.acme.Models.Trade;
import org.acme.Services.CallBankAuthService;
import org.acme.Services.CustomersService;

import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.After;
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
    CallBankAuthService callBankAuthService = new CallBankAuthService();
    GeneralController generalController= new GeneralController();
    CustomersService customerService= new CustomersService();

    public static Customer customer;
    public static Customer merchant;
    public static String caccountID;
    public static String maccountID;
    public static String transRT;

	@Given("a customer with a bank account with balance {int}")
	public void aCustomerWithABankAccountWithBalance(Integer int1) throws BankServiceException_Exception {
        customer=new Customer("Mis1","Aro1","cprMis1", int1.doubleValue(), "customer");
        String customerAccount=clientService.createAccount(customer);
        System.out.println(customerAccount);
        System.out.println(customer);
        assertTrue(true, "The bank account is created for the customer");
	}

	@Given("that the customer is registered with DTU Pay")
	public void thatTheCustomerIsRegisteredWithDTUPay() throws BankServiceException_Exception {
		customer.setBankAccount(customer_Account);
        caccountID = clientService.regDTUUser(customer);
        System.out.println(caccountID);
        assertTrue(true, "The customer is registered");
	}

	@Given("a merchant with a bank account with balance {int}")
	public void aMerchantWithABankAccountWithBalance(Integer int1) throws BankServiceException_Exception {
		merchant=new Customer("Mis1","Aro1","cprMis1", int1.doubleValue(), "customer");
        clientService.createAccount(merchant);
        System.out.println(merchant);
        assertTrue(true, "The bank account is created for the customer");
	}

	@Given("that the merchant is registered with DTU Pay")
	public void thatTheMerchantIsRegisteredWithDTUPay() throws BankServiceException_Exception {
		merchant.setBankAccount(merchant_Account);
        maccountID = clientService.regDTUUser(merchant);
        System.out.println(maccountID);
        assertTrue(true, "The customer is registered");

	}

	@When("the merchant initiates a payment for {int} kr by the customer")
	public void theMerchantInitiatesAPaymentForKrByTheCustomer(Integer int1) {
		transRT = clientService.transferMoney(new Trade(customer_Account, merchant_Account,int1.doubleValue()));
	}

	@Then("the payment is successful")
	public void thePaymentIsSuccessful() {

	}

	@Then("the balance of the customer at the bank is {int} kr")
	public void theBalanceOfTheCustomerAtTheBankIsKr(Integer int1) throws BankServiceException_Exception {
		
		int balance = clientService.getBalance(customer_Account);
//        generalController.deleteAccount(customeraccountID);
        assertEquals(int1,balance);
	}

	@Then("the balance of the merchant at the bank is {int} kr")
	public void theBalanceOfTheMerchantAtTheBankIsKr(Integer int1) throws BankServiceException_Exception {
		int balance = clientService.getBalance(merchant_Account);
		System.out.println(balance);
//      generalController.deleteAccount(customeraccountID);
      assertEquals(int1,balance);
	}
	@After
	public void afterScenario() {
		clientService.deleteAccount(customer_Account);
		clientService.deleteAccount(merchant_Account);
	}
}

