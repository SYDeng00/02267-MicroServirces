package Adapter;

import java.util.List;
import java.util.UUID;

import Domains.DTUPayAccount;
import Domains.Payment;
import Domains.Token_client;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*
Author: Siyuan Deng s232275  
*/

public class DTUPay_Interface {
	Client clientPayment = ClientBuilder.newClient();
	WebTarget dtuPayURL = clientPayment.target("http://localhost:8080/");

	public UUID create_customerDTUPayAccount(DTUPayAccount customer) {
		Response response = dtuPayURL.path("customers/accounts").request().accept(MediaType.TEXT_PLAIN)
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON));

		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
			// Read the response as a String and then convert to UUID
			String uuidString = response.readEntity(String.class);
			return UUID.fromString(uuidString);
		} else {
			// Handle non-successful response
			String responseBody = response.readEntity(String.class);
			throw new WebApplicationException("Unexpected response: " + responseBody, response.getStatus());
		}
	}

	public UUID create_merchantDTUPayAccount(DTUPayAccount customer) {
		Response response = dtuPayURL.path("customers/accounts").request().accept(MediaType.TEXT_PLAIN)
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON));

		if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
			// Read the response as a String and then convert to UUID
			String uuidString = response.readEntity(String.class);
			return UUID.fromString(uuidString);
		} else {
			// Handle non-successful response
			String responseBody = response.readEntity(String.class);
			throw new WebApplicationException("Unexpected response: " + responseBody, response.getStatus());
		}
	}

	public List<UUID> getTokens(Token_client customerDtuPay) throws Exception {
		Token_client token_client = null;
		try {
			Response response = dtuPayURL.path("customers/tokens/").request()
					.post(Entity.entity(customerDtuPay, MediaType.APPLICATION_JSON));

			System.out.println("Response Status Code: " + response.getStatus());
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {

				token_client = response.readEntity(Token_client.class);
				return token_client.getTokens();

			} else {
				return null;
			}
		} catch (ProcessingException e) {
			// Handle processing exceptions
			System.out.println("Error processing response: " + e.getMessage());
			// e.printStackTrace();
			return null;
			// throw new RuntimeException("Error processing response: " + e.getMessage(),
			// e);
		} catch (Exception e) {
			// Handle other exceptions
			throw e;
		}
	}

	public String createPayment(Payment payment) {
		try {
			Response response = dtuPayURL.path("merchant/payments").request().accept(MediaType.APPLICATION_JSON)
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

	public String getReport(UUID customerDtuPayID) {
		try {
			String id = customerDtuPayID.toString();
			Response response = dtuPayURL.path("/report/customer/" + id).request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.entity(customerDtuPayID, MediaType.APPLICATION_JSON));

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
