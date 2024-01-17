package org.acme.Repositories;

import org.acme.Domain.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * Author: Yingli
 */

public class TokenRepository {

    static TokenRepository tokenRepository = new TokenRepository();
    private static HashMap<UUID, Token> tokens = new HashMap<>();

    private TokenRepository() {
    }

    public static TokenRepository getInstance() {
        return tokenRepository;
    }

    public void addToken(Token token) {
        tokens.put(token.getToken(), token);
    }

    public UUID generateToken() {
        return UUID.randomUUID();
    }

    public List<Token> getAllTokenList() {
        return new ArrayList<>(tokens.values());
    }

    public Token getToken(UUID tokenUuid) {
        return tokens.get(tokenUuid);
    }

    public int getCustomerTokenNumer(UUID customerID) {
        int count = 0;
        for (Token token : tokens.values()) {
            if (token.getCustomerID().equals(customerID)) {
                count++;
            }
        }
        return count;
    }


    public Boolean findCustomer(UUID costomerUuid){
        for(Token token:tokens.values()){
            if(token.getCustomerID().equals(costomerUuid)){
                return true;
            }
        }
        return false;
    }

}