package Steps;

import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.acme.Controllers.GeneralController;
import org.acme.Models.Customer;
import org.acme.Services.CallBankAuthService;
import org.acme.Services.CustomersService;
import org.acme.Services.GeneralServices;
import org.junit.Assert;

import java.math.BigDecimal;

public class CustomerServiceSteps {

    private GeneralServices generalServices = new GeneralServices();

    private CustomersService customersService = new CustomersService();
    private CallBankAuthService callBankAuthService = new CallBankAuthService();
    private BigDecimal accountBalance;
    private Customer user;
    GeneralController generalController = new GeneralController();
    private String bankAccountId;

    @Before("@queriesAccountBalance")
    public void setupAccount() {
        try {
            user = new Customer("23542", "34", "23", 34.00,"customer");
            bankAccountId = callBankAuthService.CreateOneAccount(user);
            System.out.println(user);
            System.out.println(bankAccountId);
            System.out.println(user.getBalance());
        } catch (BankServiceException_Exception e) {
            System.out.println("error setting up: " + e.getMessage());
        }
    }

    @Given("a user with a bank account")
    public void aUserWithABankAccountId() {
        user.setBankAccount(bankAccountId);
        System.out.println(bankAccountId);
    }

    @When("the user queries for their account balance")
    public void theUserQueriesForTheirAccountBalance() throws Exception {
        accountBalance = callBankAuthService.checkBalance(bankAccountId);
        System.out.println(accountBalance);
    }

    @Then("the account balance should be displayed")
    public void theAccountBalanceShouldBeDisplayed() {
        Assert.assertNotNull("Account balance is null", accountBalance);
        System.out.println("Account Balance: " + accountBalance);
    }
}
