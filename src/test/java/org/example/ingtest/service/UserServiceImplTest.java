package org.example.ingtest.service;

import org.example.ingtest.model.RoleEnum;
import org.example.ingtest.model.User;
import org.example.ingtest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsernameMapsToUserDetails() {
        var entity = new User(UUID.randomUUID(), "admin", "pwd", RoleEnum.ADMIN);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(entity));

        UserDetails details = userService.loadUserByUsername("admin");

        assertThat(details.getUsername()).isEqualTo("admin");
        assertThat(details.getPassword()).isEqualTo("pwd");

        assertThat(details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .containsExactly("ROLE_ADMIN");
    }

    @Test
    void loadUserByUsernameThrowsUsernameNotFound() {
        when(userRepository.findByUsername("Iliescu")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loadUserByUsername("Iliescu"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Invalid username or password");
    }
}
