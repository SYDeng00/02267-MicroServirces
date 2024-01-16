package org.acme.business_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.acme.Domain.Token_client;
import org.acme.Domains.Message;
import org.acme.Repositories.TokenRepository;
import org.acme.Resource.TokenConfig;
import org.acme.Resoures.EventPublisher;

import com.google.gson.Gson;


public class TokenManagementServices {
	EventPublisher eventPublisher = new EventPublisher();
    TokenRepository paymentRepository = new TokenRepository();
//    private static final Logger LOG = Logger.getLogger(String.valueOf(TokenManagementServices.class));
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }
 TokenRepository repository = new TokenRepository();
    public void  generateTokens(Token_client objT) {
    	List<String> tokenList = new ArrayList<>();
    	tokenList.add(UUID.randomUUID().toString());
    	tokenList.add(UUID.randomUUID().toString());
    	tokenList.add(UUID.randomUUID().toString());
    	try {
			eventPublisher.publishEvent(
			        new Message(TokenConfig.RETURN_TOKEN1, "TokenBroker", new Object[] {tokenList}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
        // Check here how many token customer want to generate
//        int count = objT.getTokenNumber();
//        List<String> tokenListString = new ArrayList<>();
//        if (count <= HelperAttributes.MAX_TOKEN_REQ){
//            int tokenUnused = repository.validateToken(objT.getCustomerID());
//            String genToken;
//            if (tokenUnused <= HelperAttributes.MAX_UNUSED_TOKEN){
//                for (int i = 0; i < count; i++) {
//                    int tokenId = repository.getNextTokenId();
//                    genToken = repository.generateUniqueTokenId();
//                    Token token= new Token(String.valueOf(tokenId),genToken,HelperAttributes.STATUS_UNUSED,LocalDate.now(), objT.getCustomerID());
//                    repository.addIntoTokenList(token);
//                    tokenListString.add(genToken);
//                }
//                try {
//					eventPublisher.publishEvent(
//					        new Message(TokenConfig.RETURN_TOKEN1, "TokenBroker", new Object[] {tokenListString}));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//    			
//                
//
//                
//                LOG.info("Payment microservce send message to token microservce:" + TokenConfig.RETURN_TOKEN);
//                //return tokenListString;
//            }
//            LOG.info("Generating token succeed."+ tokenListString);
//        }

       // return null;
    }
}

