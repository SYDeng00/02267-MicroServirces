package org.acme.Resource;

public class TokenConfig {

    public static final String RECEIVE_RETURN_TOKEN = "requestTokens";
    public static final String SEND_TOKENS = "sendTokens";
    public static String STATUS_EXPIRED = "expired";

    
    public static String STATUS_USED = "used";
    public static String STATUS_UNUSED = "unused";
    public static int EXPIRY_DAYS_FOR_TOKEN = 2;//count in days
    public static int MAX_TOKEN_REQ = 5;
    public static int MAX_UNUSED_TOKEN = 1;
    public static int MAX_TOKEN_AVAILABLE = MAX_TOKEN_REQ + MAX_UNUSED_TOKEN;
    public static final String RECEIVE_TOKEN_VALID = "ValidTokens";
    public static final String SEND_TOKEN_VALID_RESULT = "ValidResult";
    
}
