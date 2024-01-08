package client;


import org.acme.Models.Customer;
import org.acme.Models.Trade;

import com.google.gson.JsonObject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
public class ClientService {

    Client clientPayment = ClientBuilder.newClient();
    WebTarget simplepay= clientPayment.target("http://localhost:8080/");

    public ClientService() {
    }
    

    public String createAccount(Customer customer)  {        // String jsonData = "{\"id\":1,\"cpr\":\"test_cpr1\",\"lstname\":\"last1\",\"firstname\":\"first1\",\"balence\":1000.0,\"bankAccount\":null,\"userType\":\"customer\"}";

        Response response = simplepay.path("bank")
                .request()
                .post(Entity.entity(customer, MediaType.APPLICATION_JSON));
        return response.readEntity(String.class);
    }
    
    
    public String regDTUUser(Customer customer) {
    	 Response response = simplepay.path("customers")
                 .request()
                 .post(Entity.entity(customer, MediaType.APPLICATION_JSON));
         return response.readEntity(String.class);
    }
    
    

    public String transferMoney(Trade trade) {
        System.out.println(trade);
        try {
            // Convert JsonObject to String for the request

            Response response = simplepay.path("trades")
                    .request()
                    .post(Entity.entity(trade, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(String.class);
            } else {
                // Handle non-OK responses
                System.out.println("Response Status: " + response.getStatus());
                return "Error: " + response.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return "Error: " + e.getMessage();
        }
    }
    
    public int getBalance(String id) {
    	try {
    		Response response =  simplepay.path("/accounts/"+id+"/balance")
                    .request()
                    .get();
    		//System.out.println(response.readEntity(String.class));
    		return response.readEntity(Integer.class);
    	}catch(Exception e) {
    		return 0;
    	}
    }
    
    public String deleteAccount(String id) {
        try {
            Response response = simplepay.path("/accounts/" + id) // Corrected the path
                                       .request()
                                       .delete(); // No parameter here

            // Check the response status and handle accordingly
            if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                return response.readEntity(String.class); // Or handle the response as needed
            } else {
                // Handle non-successful response, possibly returning or logging the error details
                return "Failed with status code: " + response.getStatus();
            }
        } catch(Exception e) {
            // It's usually a good practice to log the exception details
            e.printStackTrace();
            return "Error: " + e.getMessage(); // Returning more informative error message
        }
    }



    public String createTrade() {
        return null;
        //     String url = "http://localhost:8080/trades";
        //     String requestBody = jsonb.toJson(trade);
        //     return sendRequest(url, requestBody, "POST");
    }


    public String createMerchant() {
        return null;
        //     String url = "http://localhost:8080/merchants";
        //     String requestBody = jsonb.toJson(merchant); // 将商户对象转换为 JSON
        //     return sendRequest(url, requestBody, "POST");
    }

    public String sendRequest(String url, String requestBody, String requestType) {
        return null;
        // HttpRequest request;
        // if ("POST".equals(requestType)) {
        //     request = HttpRequest.newBuilder()
        //             .uri(URI.create(url))
        //             .header("Content-Type", "application/json")
        //             .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        //             .build();
        // } else { // Assume GET for any other type
        //     request = HttpRequest.newBuilder()
        //             .uri(URI.create(url))
        //             .GET()
        //             .build();
        // }

        // try {
        //     HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        //     return response.body();
        // } catch (IOException e) {
        //     System.err.println("Error in network communication: " + e.getMessage());
        // } catch (InterruptedException e) {
        //     System.err.println("Request interrupted: " + e.getMessage());
        // } catch (Exception e) {
        //     System.err.println("An error occurred: " + e.getMessage());
        //     if (e instanceof BankServiceException_Exception) {
        //         handleBankServiceException((BankServiceException_Exception) e);
        //     }
        // }
        // return null;
    }



    public String createPayment() {
        return null;
        //     String url = "http://localhost:8080/payments";
        //     return sendRequest(url, paymentData, "POST");
    }

    public String getPayments() {
        return null;
        // String url = "http://localhost:8080/payments";
        // return sendRequest(url, "", "GET");
    }

}


// class Trade {
//     private String customerBankAccount;
//     private String merchantBankAccount;
//     private Double amount;

//     public Trade() {}

//     public Trade(String customerBankAccount, String merchantBankAccount, Double amount) {
//         this.customerBankAccount = customerBankAccount;
//         this.merchantBankAccount = merchantBankAccount;
//         this.amount = amount;
//     }

//     // getter 和 setter 方法
//     public String getCustomerBankAccount() {
//         return customerBankAccount;
//     }

//     public void setCustomerBankAccount(String customerBankAccount) {
//         this.customerBankAccount = customerBankAccount;
//     }

//     public String getMerchantBankAccount() {
//         return merchantBankAccount;
//     }

//     public void setMerchantBankAccount(String merchantBankAccount) {
//         this.merchantBankAccount = merchantBankAccount;
//     }

//     public Double getAmount() {
//         return amount;
//     }

//     public void setAmount(Double amount) {
//         this.amount = amount;
//     }
//}
