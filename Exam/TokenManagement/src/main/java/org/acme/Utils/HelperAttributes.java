package org.acme.Utils;

public class HelperAttributes {
    public static String STATUS_USED = "used";
    public static String STATUS_UNUSED = "unused";
    public static String STATUS_EXPIRED = "expired";
    public static int EXPIRY_DAYS_FOR_TOKEN = 2;//count in days
    public static int MAX_TOKEN_REQ = 5;
    public static int MAX_UNUSED_TOKEN = 1;
    public static int MAX_TOKEN_AVAILABLE = MAX_TOKEN_REQ + MAX_UNUSED_TOKEN;
}
