package snippet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.acme.HelloService;
import org.acme.Person;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HelloServiceSteps {

	String result;
	HelloService service= new HelloService();
	Person person_detail;

	@When("I call the hello service")
	public void i_call_the_hello_service() {
		result = service.hello();
	}

	@Then("I get the answer {string}")
	public void i_get_the_answer(String string) {
		assertEquals(string, result);
	}

	@When("I call the person service")
	public void i_call_the_person_service() {
		person_detail = service.getPerson();
	}

	@Then("I get {string}")
	public void i_get(String string) {
		assertEquals(string, person_detail);
	}
}
