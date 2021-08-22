package com.example.appuserauthentication;

import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThrows;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

  private static final String TEST_USER_NAME = "TestUserName";
  private static final String TEST_PASSWORD = "TestPassword";
  private static final String TEST_ROLE = "ROLE_USER";
  private static final String TEST_ADDRESS = "Some address";
  private static final int TEST_AGE = 25;

  private final UserRepository userRepository = mock(UserRepository.class);
  private final CustomUserDetailsService detailsService =
      new CustomUserDetailsService(userRepository);

  @Test
  public void validUserTest() {
    when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(getTestUser()));

    CustomUserDetails userDetails =
        (CustomUserDetails) detailsService.loadUserByUsername(TEST_USER_NAME);

    assertEquals(TEST_USER_NAME, userDetails.getUsername());
    assertEquals(TEST_PASSWORD, userDetails.getPassword());
    assertTrue(userDetails.isEnabled());
    assertTrue(userDetails.isAccountNonExpired());
    assertTrue(userDetails.isAccountNonLocked());
    assertTrue(userDetails.isCredentialsNonExpired());
    assertEquals(
        Collections.singletonList(new SimpleGrantedAuthority(TEST_ROLE)),
        userDetails.getAuthorities());
    assertEquals(TEST_USER_NAME, userDetails.getUsername());
    assertEquals(TEST_AGE, userDetails.getAge());
    assertEquals(TEST_ADDRESS, userDetails.getAddress());
  }

  @Test
  public void exceptionTest() {
    when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

    UsernameNotFoundException exception =
        assertThrows(
            UsernameNotFoundException.class,
            () -> detailsService.loadUserByUsername(TEST_USER_NAME));
    assertEquals(
        "User with username " + TEST_USER_NAME + " was not found!", exception.getMessage());
  }

  private User getTestUser() {
    User user = new User();
    user.setId(16);
    user.setUserName(TEST_USER_NAME);
    user.setPassword(TEST_PASSWORD);
    user.setActive(true);
    user.setAge(TEST_AGE);
    user.setAddress(TEST_ADDRESS);
    user.setRoles(TEST_ROLE);
    return user;
  }
}
