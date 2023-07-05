package fr.fms.security;

public class SecurityConstants {

    public static final String HEADER_STRING = "Authorization";
    public static final String SECRET = "elbab@gmail.com";
    public static final long EXPIRATION_TIME = 10 * 60 * 1000; //10 minutes = 10 * 60 * 1000ms
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ERROR_MSG = "error-message";
}
