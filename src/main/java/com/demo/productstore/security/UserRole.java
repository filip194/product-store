package com.demo.productstore.security;

/**
 * Defines user roles for the application.
 */
public class UserRole {
    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    public static final String USER_ROLE = "ROLE_USER";
    public static final String AUTHENTICATED_USER_ROLE = "ROLE_AUTHENTICATED_USER";

    /**
     * Returns all roles defined in this class.
     *
     * @return an array of role strings
     */
    public static String[] getAllRoles() {
        return new String[]{ADMIN_ROLE, USER_ROLE, AUTHENTICATED_USER_ROLE};
    }
}
