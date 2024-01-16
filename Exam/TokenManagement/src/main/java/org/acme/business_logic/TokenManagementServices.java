package org.acme.business_logic;

import com.google.gson.Gson;
import io.vertx.core.eventbus.Message;
import org.acme.Domain.Token_client;
import org.acme.Repositories.TokenRepository;
import org.acme.Resource.TokenConfig;
import org.acme.Resoures.EventPublisher;
import org.acme.Utils.HelperAttributes;
import org.acme.Domain.Token;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class TokenManagementServices {
    EventPublisher eventPublisher = new EventPublisher();
    TokenRepository paymentRepository = new TokenRepository();
    private static final Logger LOG = Logger.getLogger(String.valueOf(TokenManagementServices.class));
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }
 TokenRepository repository = new TokenRepository();
    public void  generateTokens(Token_client objT, Object[] payload) {
        // Check here how many token customer want to generate
        int count = Integer.valueOf(objT.getTokenNumber());
        List<String> tokenListString = new ArrayList<>();
        if (count <= HelperAttributes.MAX_TOKEN_REQ){
            int tokenUnused = repository.validateToken(objT.getCustomerID());
            String genToken;
            if (tokenUnused <= HelperAttributes.MAX_UNUSED_TOKEN){
                for (int i = 0; i < count; i++) {
                    int tokenId = repository.getNextTokenId();
                    genToken = repository.generateUniqueTokenId();
                    Token token= new Token(String.valueOf(tokenId),genToken,HelperAttributes.STATUS_UNUSED,LocalDate.now(), objT.getCustomerID());
                    repository.addIntoTokenList(token);
                    tokenListString.add(genToken);
                }
                /*eventPublisher.publishEvent(
                        new Message(TokenConfig.RETURN_TOKEN, "TokenBroker", new Object[] {tokenListString}));
                LOG.info("Payment microservce send message to token microservce:" + TokenConfig.RETURN_TOKEN);
*/
                //return tokenListString;
            }
            LOG.info("Generating token succeed."+ tokenListString);
        }

       // return null;
    }
}

