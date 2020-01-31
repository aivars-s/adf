package adf.homework.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import adf.homework.domain.Role;
import adf.homework.domain.User;
import adf.homework.repository.UserRepository;
import adf.homework.security.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTests {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .id(1L)
            .username("admin")
            .password(encoder.encode("admin"))
            .role(Role.ADMIN)
            .build();
    }

    @Test
    public void loadUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails details = userDetailsService.loadUserByUsername("admin");

        assertTrue(new ReflectionEquals(new UserDetailsImpl(user)).matches(details));

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void loadUserByUsername_notFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("doesNotExist");
        });

        verify(userRepository, times(1)).findByUsername(anyString());
    }

}
