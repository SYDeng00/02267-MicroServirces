package org.acme.Resource;

public class TokenConfig {

    public static final String RETURN_TOKEN = "return Token";
    public static final String RETURN_TOKEN1 = "return Token1";
    public static String STATUS_EXPIRED = "expired";
    public static int EXPIRY_DAYS_FOR_TOKEN = 2;//count in days
    public static int MAX_TOKEN_REQ = 5;
    public static int MAX_UNUSED_TOKEN = 1;
    public static int MAX_TOKEN_AVAILABLE = MAX_TOKEN_REQ + MAX_UNUSED_TOKEN;
    public static final String RECEIVE_TOKEN_VALID = "ValidTokens";
    public static final String SEND_TOKEN_VALID_RESULT = "ValidResult";
    
}
