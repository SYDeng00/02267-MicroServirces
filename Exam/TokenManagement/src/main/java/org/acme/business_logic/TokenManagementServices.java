package org.acme.business_logic;

import com.google.gson.Gson;
import org.acme.Resoures.EventPublisher;
import org.acme.Utils.HelperAttributes;
import org.acme.Domain.Token;
import org.acme.Repositories.TokenRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class TokenManagementServices {
    EventPublisher eventPublisher = new EventPublisher();
    TokenRepository tokenRepository = TokenRepository.getInstance();
    private static final Logger LOG = Logger.getLogger(String.valueOf(TokenManagementServices.class));

    public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        return gson.fromJson(json, objectClass);
    }

    // In TokenManagementServices class

    public List<String> generateTokens(Object[] payload) {
        Token objT = typeTransfer(payload[0], Token.class);
        List<String> generatedTokens = new ArrayList<>();

        if (objT.getTokenCount() <= HelperAttributes.MAX_TOKEN_REQ) {
            // Assuming customerID in Token object is now a String
            String customerID = objT.getCustomerID();
            int tokenUnused = validateToken(UUID.fromString(customerID)); // Adjusted to use String customerID

            if (tokenUnused <= HelperAttributes.MAX_UNUSED_TOKEN) {
                for (int i = 0; i < objT.getTokenCount(); i++) {
                    String genToken = generateUniqueTokenId();
                    Token token = new Token(genToken, HelperAttributes.STATUS_UNUSED, LocalDate.now(), customerID); // Using String customerID
                    tokenRepository.addToken(token);
                    generatedTokens.add(genToken);
                }
            }
        }
        LOG.info("Tokens generated for customer ID: " + objT.getCustomerID());
        return generatedTokens;
    }



    public int validateToken(UUID customerID) {
        List<Token> tokens = tokenRepository.getTokensByCustomerId(String.valueOf(customerID));
        return (int) tokens.stream().filter(t -> t.getTokenStatus().equals(HelperAttributes.STATUS_UNUSED)).count();
    }

    private String generateUniqueTokenId() {
        return UUID.randomUUID().toString();
    }

    public List<Token> getAllTokenList() {
        return tokenRepository.getAllTokens();
    }
}
