package org.acme.business_logic;

import com.google.gson.Gson;
import org.acme.Resoures.EventPublisher;
import org.acme.Utils.HelperAttributes;
import org.acme.Domain.Token;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class TokenManagementServices {
    EventPublisher eventPublisher = new EventPublisher();
    TokenRepository paymentRepository =  TokenRepository.getInstance();
    private static final Logger LOG = Logger.getLogger(TokenManagementServices.class);
    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }
    private List<Token> tokenList = new ArrayList<>();
    public void  generateTokens(Token objT,Object[] payload) {
        // Check here how many token customer want to generate
        List<String> tokenListString = new ArrayList<>();
        if (objT.getTokenCount() <= HelperAttributes.MAX_TOKEN_REQ){
            int tokenUnused = validateToken(objT.getCustomerID());
            String genToken;
            if (tokenUnused <= HelperAttributes.MAX_UNUSED_TOKEN){
                for (int i = 0; i < objT.getTokenCount(); i++) {
                    int tokenId = tokenList.size() + 1;
                    genToken = generateUniqueTokenId();
                    Token token= new Token(String.valueOf(tokenId),genToken,HelperAttributes.STATUS_UNUSED,LocalDate.now(), objT.getCustomerID());
                    tokenList.add(token);
                    tokenListString.add(genToken);
                }
                //return tokenListString;
            }
            LOG.info("Payment information resolve succeed:" + PaymentConfig.RECEIVE_MERCHANT_ASK_PAYMENT + "-->"
                    + paymentID.toString() + merchanUuid.toString() + amount);
        }

       // return null;
    }

    public int validateToken(String cid) {
        return Math.toIntExact(tokenList.stream().filter(t -> t.getCustomerID() == cid && Objects.equals(t.getTokenStatus(), HelperAttributes.STATUS_UNUSED)).count());
    }

    private String generateUniqueTokenId() {
        return UUID.randomUUID().toString();
    }

    public List<Token> getAllTokenList() { return tokenList; }
}

