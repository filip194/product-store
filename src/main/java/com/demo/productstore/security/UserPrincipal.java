package com.demo.productstore.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Represents the principal details of a user for authentication and authorization purposes.
 *
 * @param username    the username of the user
 * @param password    the password of the user
 * @param authorities the collection of granted authorities for the user
 */
public record UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities)
        implements UserDetails {

    public UserPrincipal {
        Assert.notNull(username, "username should not be null");
        Assert.notNull(password, "password should not be null");
        Assert.notEmpty(authorities, "authorities should not be null or empty");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

