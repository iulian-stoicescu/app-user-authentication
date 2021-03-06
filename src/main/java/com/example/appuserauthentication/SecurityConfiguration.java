package com.example.appuserauthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  @Autowired
  public SecurityConfiguration(
      UserDetailsService userDetailsService, CustomAccessDeniedHandler customAccessDeniedHandler) {
    super();
    this.userDetailsService = userDetailsService;
    this.customAccessDeniedHandler = customAccessDeniedHandler;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/admin")
        .hasRole("ADMIN")
        .antMatchers("/user")
        .hasAnyRole("ADMIN", "USER")
        .antMatchers("/")
        .permitAll()
        .and()
        .formLogin()
        .defaultSuccessUrl("/user", true)
        .and()
        .exceptionHandling()
        .accessDeniedHandler(customAccessDeniedHandler);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
