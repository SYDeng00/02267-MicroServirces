package org.acme.business_logic;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.acme.Domain.Token;
import org.acme.Domains.Message;
import org.acme.Repositories.TokenRepository;
import org.acme.Resource.TokenConfig;
import org.acme.Resoures.EventPublisher;

import com.google.gson.Gson;
import org.acme.Utils.HelperAttributes;

import static io.quarkus.arc.ComponentsProvider.LOG;

public class TokenManagementServices {
	EventPublisher eventPublisher = new EventPublisher();
	TokenRepository repository = TokenRepository.getInstance();

	// gger.getLogger(String.valueOf(TokenManagementServices.class));
	public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
		Gson gson = new Gson();
		String json = gson.toJson(payload);
		return gson.fromJson(json, objectClass);
	}

	public void generateTokens(Object[] payload) {
		LOG.info("generateTokens");
		UUID customerID = typeTransfer(payload[0], UUID.class);
		int request_token_num = typeTransfer(payload[1], Integer.class);
		Message message;
		LOG.info("Get cUUID:"+ customerID + "RTN:" + String.valueOf(request_token_num));
		
		// Check here how many token customer want to generate

		if (/*request_token_num < 1 ||*/ request_token_num > 5) {
			message = new Message(TokenConfig.SEND_TOKENS, "TokenFacadeBroker",
					new Object[] { customerID,"Request number invalid" });
			message.setStatus("404");
			try {
				eventPublisher.publishEvent(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		LOG.info("RTN ok:");
		if(repository.findCustomer(customerID) && repository.getCustomerTokenNumer(customerID) > 1){
			message = new Message(TokenConfig.SEND_TOKENS, "TokenFacadeBroker",
					new Object[] { customerID,"Stored token numebr is more than 1" });
			message.setStatus("404");
			try {
				eventPublisher.publishEvent(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		LOG.info("User not exist");
		// if (repository.getCustomerTokenNumer(customerID) > 1) {
		// 	message = new Message(TokenConfig.RETURN_TOKEN1, "TokenFacadeBroker",
		// 			new Object[] { "Stored token numebr is more than 1" });
		// 	message.setStatus("404");
		// 	try {
		// 		eventPublisher.publishEvent(message);
		// 	} catch (Exception e) {
		// 		// TODO Auto-generated catch block
		// 		e.printStackTrace();
		// 	}
		// 	return;
		// }
		List<UUID> tokensList = new ArrayList<>();
		for (int i = 0; i < request_token_num; i++) {
			UUID tokenID = repository.generateToken();
			Token token = new Token(tokenID, HelperAttributes.STATUS_UNUSED,
					LocalDate.now(), customerID);
			repository.addToken(token);
			tokensList.add(tokenID);
		}
		try {
			eventPublisher.publishEvent(
					new Message(TokenConfig.SEND_TOKENS, "TokenFacadeBroker", new Object[] {customerID, tokensList }));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Payment microservce send message to token microservce:" + TokenConfig.RECEIVE_RETURN_TOKEN);
		// return tokenListString;
	}

	
	public void tokenValidate(Object[] payload) throws Exception {
		UUID tokenUuid = typeTransfer(payload[1], UUID.class);
		Token token = repository.getToken(tokenUuid);
		LocalDate expirationDate = token.getTokenCreateDate().plus(TokenConfig.EXPIRY_DAYS_FOR_TOKEN, ChronoUnit.DAYS);
		String reason = "";

		if (LocalDate.now().isAfter(expirationDate) && token.getValid()) {
			reason = "Token expired";
			token.setValid(false);
		}
		if (!LocalDate.now().isAfter(expirationDate) && !token.getValid()) {
			reason = "Token has been used";
			token.setValid(false);
		}
		eventPublisher.publishEvent(
				new Message(
						TokenConfig.SEND_TOKEN_VALID_RESULT,
						"PaymentBroker",
						new Object[] { payload[0], token.getValid(), reason, token.getCustomerID() }));
	}

}
