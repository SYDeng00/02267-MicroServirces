package org.acme.Repositories;
import org.acme.Domain.Token;
import org.acme.Utils.HelperAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TokenRepository {

    static TokenRepository tokenRepository = new TokenRepository();
    private TokenRepository(){}
    public static TokenRepository getInstance(){
        return tokenRepository;
    }
    private static List<Token> tokenList = new ArrayList<>();
    public void addIntoTokenList(Token token){
        tokenList.add(token);
    }

    public int getNextTokenId() {
        return tokenList.size() + 1;
    }
    public int validateToken(String cid) {
        return Math.toIntExact(tokenList.stream().filter(t -> t.getCustomerID() == cid && Objects.equals(t.getTokenStatus(), HelperAttributes.STATUS_UNUSED)).count());
    }
    public String generateUniqueTokenId() {
        return UUID.randomUUID().toString();
    }

    public List<Token> getAllTokenList() { return tokenList; }
}