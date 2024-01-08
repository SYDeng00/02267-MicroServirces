package Steps;

import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.acme.Controllers.GeneralController;
import org.acme.Models.Customer;
import org.acme.Services.CallBankAuthService;
import org.acme.Services.GeneralServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegisterSteps {

    private Customer user;
    GeneralController generalController = new GeneralController();
    private GeneralServices generalServices = new GeneralServices();
    CallBankAuthService callBankAuthService = new CallBankAuthService();
    private String registrationResult;
    private Boolean bankAccountResult;
    private String bankAccountId;

    private String stringValue;

    @Before("@register")
    public void setupAccount() {
        try {
            user = new Customer("sfg", "42523", "43523", 34.00);
            bankAccountId = callBankAuthService.CreateOneAccount(user);
            System.out.println(user);
            System.out.println(bankAccountId);
        } catch (BankServiceException_Exception e) {
            System.out.println("error setting up: " + e.getMessage());
        }
    }

    @Given("has a bank account with a valid bank account id")
    public void hasABankAccountWithAValidBankAccountId() {
        assertNotNull(bankAccountId);
        System.out.println(bankAccountId + "1");
    }

    @And("provides their details to DTUPay starting with first name, last name, CPR & Bank account id")
    public void providesTheirDetailsToDTUPayStartingWithFirstNameLastNameCPRBankAccountNr() {
        user.setBankAccount(bankAccountId);
        System.out.println(user);
    }

    @Then("is registering with DTUPay as a customer")
    public void isRegisteredWithDTUPayAsACustomer() throws BankServiceException_Exception {
        registrationResult = generalController.regDtuPayUser(user);
        System.out.println(registrationResult);
    }

    @Given("user gives non existing bank account id")
    public void userGivesNonExistingBankAccountId() throws BankServiceException_Exception {
        bankAccountResult = callBankAuthService.validAccount("347509");
        System.out.println(bankAccountResult);
        stringValue = String.valueOf(bankAccountResult);
    }

    @Then("is given an error message")
    public void isGivenAnErrorMessage() {
        assertEquals("false", stringValue);
    }
}

