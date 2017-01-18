package edu.umdearborn.astronomyapp.config;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.ADMIN_PATH_PATTERNS;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH_PATTERNS;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.LOGOUT_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.LOGOUT_SUCCESS_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.PUBLIC_PATH_PATTERNS;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.SESSION_EXPIRED_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.SESSION_INVALID_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH_PATTERNS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import edu.umdearborn.astronomyapp.config.annotation.Dev;
import edu.umdearborn.astronomyapp.config.annotation.Prod;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Prod
  @Bean("passwordEncoder")
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .httpBasic()
        .and()
      .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
      .logout()
        .logoutUrl(LOGOUT_PATH)
        .logoutSuccessUrl(LOGOUT_SUCCESS_PATH)
        .deleteCookies("SESSION")
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .and()
      .authorizeRequests()
        .filterSecurityInterceptorOncePerRequest(false)
        .antMatchers(PUBLIC_PATH_PATTERNS).permitAll()
        .antMatchers(ADMIN_PATH_PATTERNS).hasRole("ADMIN")
        .antMatchers(INSTRUCTOR_PATH_PATTERNS).hasRole("INSTRUCTOR")
        .antMatchers(STUDENT_PATH_PATTERNS).hasRole("USER")
        .anyRequest().fullyAuthenticated()
        .and()
      .sessionManagement()
        .invalidSessionUrl(SESSION_INVALID_PATH)
        .sessionFixation().changeSessionId()
        .sessionFixation().newSession()
        .maximumSessions(1)
        .expiredUrl(SESSION_EXPIRED_PATH);
    // @formatter:on
  }

  @Dev
  @Bean("passwordEncoder")
  public PasswordEncoder noOpPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}
