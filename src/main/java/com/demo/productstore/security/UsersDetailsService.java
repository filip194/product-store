package com.demo.productstore.security;

import com.demo.productstore.security.user.db.UserEntity;
import com.demo.productstore.security.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

/**
 * Service for loading user-specific data.
 * <p>
 * This class implements the UserDetailsService interface to retrieve user details
 * from the database and assign authorities based on user roles.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository repository;

    /**
     * Loads the user details by username.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated UserDetails object (never null)
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching user details for user '{}'", username);
        final UserEntity user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

        final Collection<? extends GrantedAuthority> authorities = assignAuthoritiesToUser(user);
        final String password = user.getPassword();
        return new UserPrincipal(username, password, authorities);
    }

    /**
     * Assigns authorities to the user based on their roles.
     *
     * @param userEntity the user entity
     * @return a collection of granted authorities
     */
    public Collection<? extends GrantedAuthority> assignAuthoritiesToUser(UserEntity userEntity) {
        final Collection<UserAuthority> authorities = new HashSet<>();
        userEntity.getRoles().forEach(roleEntity -> authorities.add(UserAuthority.valueOf(roleEntity.getName())));
        authorities.add(UserAuthority.AUTHENTICATED_USER);
        return authorities;
    }
}

