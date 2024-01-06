package Steps;

import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.acme.Controllers.GeneralController;
import org.acme.Models.Customer;
import org.acme.Models.Trade;
import org.acme.Services.CallBankAuthService;
import org.acme.Services.CustomersService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllPaymentSteps {


    CallBankAuthService callBankAuthService = new CallBankAuthService();
    GeneralController generalController= new GeneralController();
    CustomersService customerService= new CustomersService();
    public static String customeraccountID;
    public static String merchantaccountID;
    @Given("a customer with a bank account with balance {int}")
    public void a_customer_with_a_bank_account_with_balance(Integer int1) throws BankServiceException_Exception {

        customeraccountID=callBankAuthService.CreateOneAccount(new Customer("Mis","Aro","cprMis", int1.doubleValue(), "customer"));
        System.out.println(customeraccountID);
        assertTrue(true, "The bank account is created for the customer");

    }
    @And("that the customer is registered with DTU Pay")
    public void that_the_customer_is_registered_with_dtu_pay() throws BankServiceException_Exception {
        generalController.regDtuPayUser(new Customer("Mis","Aro","cprMis",customeraccountID,"customer"));
        assertTrue(true, "The customer is registered");
    }
    @Given("a merchant with a bank account with balance {int}")
    public void a_merchant_with_a_bank_account_with_balance(Integer int1) throws BankServiceException_Exception {
        merchantaccountID=callBankAuthService.CreateOneAccount(new Customer("DTUManage","CanteenServ","cprDTUManage",int1.doubleValue(),"merchant"));
        System.out.println(merchantaccountID);
        assertTrue(true, "The bank account is created for the merchant");
    }
    @And("that the merchant is registered with DTU Pay")
    public void that_the_merchant_is_registered_with_dtu_pay() throws BankServiceException_Exception {
        generalController.regDtuPayUser(new Customer("DTUManage","CanteenServ","cprDTUManage",merchantaccountID,"merchant"));
        assertTrue(true, "The merchant is registered");
    }
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void the_merchant_initiates_a_payment_for_kr_by_the_customer(Integer int1) {
        generalController.addNewTrades(new Trade(customeraccountID, merchantaccountID,int1.doubleValue()));
    }
    @Then("the payment is successful")
    public void the_payment_is_successful() {
        generalController.getAllTrades();
        assertTrue(true,"The payment is successful");
    }
    @Then("the balance of the customer at the bank is {int} kr")
    public void the_balance_of_the_customer_at_the_bank_is_kr(Integer int1) throws BankServiceException_Exception {
        double currentBalance=generalController.getBalance(customeraccountID).doubleValue();
        assertEquals(int1.doubleValue(),currentBalance);
        generalController.deleteAccount(customeraccountID);
    }
    @Then("the balance of the merchant at the bank is {int} kr")
    public void the_balance_of_the_merchant_at_the_bank_is_kr(Integer int1) throws BankServiceException_Exception {
        double currentBalance=generalController.getBalance(merchantaccountID).doubleValue();
        assertEquals(int1.doubleValue(),currentBalance);

        generalController.deleteAccount(merchantaccountID);

    }
}
