package Domains;

import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.ProcessingException;
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
	public UUID createDTUPayAccount(DTUPayAccount customer) {
		Response response = dtuPayURL.path("accounts").request()
				.post(Entity.entity(customer, MediaType.APPLICATION_JSON));
		return response.readEntity(UUID.class);

	}

	public List<UUID> getTokens(Token_client customerDtuPay) throws Exception {
		Token_client token_client=null;
	    try {
	        Response response = dtuPayURL.path("/tokens/").request()
	                .post(Entity.entity(customerDtuPay, MediaType.APPLICATION_JSON));
	        
	        System.out.println("Response Status Code: " + response.getStatus());
	        if (response.getStatus() == Response.Status.OK.getStatusCode()) {

					token_client= response.readEntity(Token_client.class);
					return  token_client.getTokens();

	        } else {
	            return null;
	        }
	    } catch (ProcessingException e) {
	        // Handle processing exceptions
			System.out.println("Error processing response: " + e.getMessage());
			//e.printStackTrace();
			return null;
			//throw new RuntimeException("Error processing response: " + e.getMessage(), e);
	    } catch (Exception e) {
	        // Handle other exceptions
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

	public String getReport(UUID customerDtuPayID) {
		try {
			Response response = dtuPayURL.path("/report/customer" +  customerDtuPayID).request()
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
