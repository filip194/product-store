package com.demo.productstore.security;

import com.demo.productstore.fixture.RoleFixture;
import com.demo.productstore.fixture.UserFixture;
import com.demo.productstore.security.user.db.UserEntity;
import com.demo.productstore.security.user.db.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsersDetailsService usersDetailsService;

    @Test
    void shouldLoadUserByUsername_UserFound() {
        final UserEntity user = UserFixture.createUserEntity();
        user.setRoles(Set.of(RoleFixture.createAdminRoleEntity(), RoleFixture.createAuthenticatedUserRoleEntity()));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        final UserDetails userDetails = usersDetailsService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.ADMIN_ROLE)));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.AUTHENTICATED_USER_ROLE)));
    }

    @Test
    void shouldFindUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> usersDetailsService.loadUserByUsername("unknown"));
    }

    @Test
    void shouldAssignAuthoritiesToUser_AddsAuthenticatedUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final UserEntity user = UserFixture.createUserEntity();
        user.setRoles(Set.of(RoleFixture.createAdminRoleEntity(), RoleFixture.createAuthenticatedUserRoleEntity()));

        final Collection<? extends GrantedAuthority> authorities = usersDetailsService.assignAuthoritiesToUser(user);

        assertTrue(((java.util.Collection<?>) authorities).stream()
                .anyMatch(a -> ((org.springframework.security.core.GrantedAuthority) a)
                        .getAuthority().equals(UserRole.AUTHENTICATED_USER_ROLE)));
        assertTrue(((java.util.Collection<?>) authorities).stream()
                .anyMatch(a -> ((org.springframework.security.core.GrantedAuthority) a)
                        .getAuthority().equals(UserRole.ADMIN_ROLE)));
    }
}
