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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
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
    void testLoadUserByUsername_UserFound() {
        UserEntity user = UserFixture.createUserEntity();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRoles(Set.of(RoleFixture.createRoleEntity()));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        var userDetails = usersDetailsService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("AUTHENTICATED_USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> usersDetailsService.loadUserByUsername("unknown"));
    }

    @Test
    void testAssignAuthoritiesToUser_AddsAuthenticatedUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UserEntity user = UserFixture.createUserEntity();
        user.setRoles(Collections.emptySet());

        var authorities = usersDetailsService
                .getClass()
                .getDeclaredMethod("assignAuthoritiesToUser", UserEntity.class)
                .invoke(usersDetailsService, user);

        assertTrue(((java.util.Collection<?>) authorities).stream()
                .anyMatch(a -> ((org.springframework.security.core.GrantedAuthority) a)
                        .getAuthority().equals("AUTHENTICATED_USER")));
    }
}
