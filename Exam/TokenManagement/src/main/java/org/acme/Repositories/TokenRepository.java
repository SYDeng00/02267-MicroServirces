package org.acme.Repositories;



import org.acme.Domain.Token;
import org.acme.Interfaces.ITokenRepository;

import java.util.*;
import java.util.stream.Collectors;

public class TokenRepository implements ITokenRepository {
    private static final HashMap<String, Token> tokens = new HashMap<>();

    private TokenRepository() {
        // Private constructor to enforce singleton pattern
    }

    public static TokenRepository getInstance() {
        return new TokenRepository();
    }

    public void addToken(Token token) {
        tokens.put(token.getTokenID(), token);
    }

    public Token getToken(String tokenId) {
        return tokens.get(tokenId);
    }

    @Override
    public List<Token> getTokensByCustomerId(String customerId) {
        return tokens.values().stream()
                .filter(token -> token.getCustomerID().equals(customerId))
                .collect(Collectors.toList());
    }


    public void invalidateToken(String tokenId) {
        Token token = tokens.get(tokenId);
        if (token != null) {
            token.setValid(false);
        }
    }

    public List<Token> getAllTokens() {
        return new ArrayList<>(tokens.values());
    }
}

