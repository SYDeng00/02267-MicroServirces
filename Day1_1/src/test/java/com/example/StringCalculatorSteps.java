package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorSteps {
    private StringCalculator calculator;
    private int result;

    @Given("a calculator")
    public void a_calculator() {
        calculator = new StringCalculator();
    }

    @When("I add {string}")
    public void i_add(String numbers) {
        result = calculator.add(numbers);
    }

    @Then("the result should be {int}")
    public void the_result_should_be(int expected) {
        assertEquals(expected, result);
    }


}

