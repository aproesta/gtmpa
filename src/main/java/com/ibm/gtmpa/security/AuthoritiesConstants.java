package com.ibm.gtmpa.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String BP = "ROLE_BP";
    public static final String BP_MANAGER = "ROLE_BP_MANAGER";
    public static final String CHANNEL_MANAGER = "ROLE_CHANNEL_MANAGER";
    public static final String BUS_OPS = "ROLE_BUS_OPS";

    private AuthoritiesConstants() {
    }
}
