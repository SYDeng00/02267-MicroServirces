package Domains;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class DTUPay_Interface {
	Client clientPayment = ClientBuilder.newClient();
	WebTarget dtuPayURL = clientPayment.target("http://localhost:8080/");

	//
	public String createDTUPayAccount(DTUPayAccount customer) {
		Response response = dtuPayURL.path("accounts").request()
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		return response.readEntity(String.class);

	}

	public List<String> getTokens(UUID customerDtuPayID) throws Exception {
		try {
			Response response = dtuPayURL.path("/tokens/" + customerDtuPayID).request()
					.post(Entity.entity(customerDtuPayID, MediaType.APPLICATION_JSON));

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				return response.readEntity(new GenericType<List<String>>() {
				});
			} else {

				System.out.println("Error: " + response.getStatus());
				System.out.println("Error Body: " + response.readEntity(String.class));
				return Collections.emptyList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String createPayment(Payment payment) {
		try {
			Response response = dtuPayURL.path("/payments").request()
					.post(Entity.entity(payment, MediaType.APPLICATION_JSON));

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				return response.readEntity(String.class);
			} else {

				System.out.println("Error: " + response.getStatus());
				System.out.println("Error Body: " + response.readEntity(String.class));
				return "payment fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}

}
