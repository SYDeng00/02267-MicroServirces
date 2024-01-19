package org.acme.Interfaces;

import org.acme.Domain.Token;
import java.util.List;

/**
 * @Author:  Divya
 */

public interface ITokenRepository {
    void addToken(Token token);
    Token getToken(String tokenId);
    List<Token> getTokensByCustomerId(String customerId);
    void invalidateToken(String tokenId);
    List<Token> getAllTokens();
}

