package domin;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class DTUPay_Interface {
    Client clientPayment = ClientBuilder.newClient();
    WebTarget dtuPayURL= clientPayment.target("http://localhost:8080/");
	
    
    //
    public String createDTUPayAccount(DTUPayAccount customer) {
   	 Response response = dtuPayURL.path("customers")
             .request()
             .post(Entity.entity(customer, MediaType.APPLICATION_JSON));
     return response.readEntity(String.class);
        
	}
    
    
}
