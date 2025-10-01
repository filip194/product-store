package com.demo.productstore.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum representing user authorities in the system.
 */
public enum UserAuthority implements GrantedAuthority {

    USER(UserRole.USER_ROLE),
    ADMIN(UserRole.ADMIN_ROLE),
    AUTHENTICATED_USER(UserRole.AUTHENTICATED_USER_ROLE);

    UserAuthority(String authority) {
        this.authority = authority;
    }

    private final String authority;

    /**
     * Returns the authority string associated with the user role.
     *
     * @return the authority string
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }
}

