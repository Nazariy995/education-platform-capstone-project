package edu.umdearborn.astronomyapp.config;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.ADMIN_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.LOGOUT_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.LOGOUT_SUCCESS_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.SESSION_EXPIRED_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.SESSION_INVALID_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
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
        .authenticationEntryPoint(new Http401AuthenticationEntryPoint("Newauth realm=\"astro-app\""))
        .and()
      .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
      .logout()
        .logoutUrl(LOGOUT_PATH)
        .logoutSuccessUrl(LOGOUT_SUCCESS_PATH)
        .deleteCookies("XSRF-TOKEN")
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .and()
      .authorizeRequests()
        .filterSecurityInterceptorOncePerRequest(true)
        .antMatchers(REST_PATH_PREFIX).fullyAuthenticated()
        .antMatchers(REST_PATH_PREFIX + ADMIN_PATH + "/**").hasRole("ADMIN")
        .antMatchers(REST_PATH_PREFIX + INSTRUCTOR_PATH + "/**").hasRole("INSTRUCTOR")
        .antMatchers(REST_PATH_PREFIX + STUDENT_PATH + "/**").hasRole("USER")
        .anyRequest().permitAll()
        .and()
      .sessionManagement()
        .invalidSessionUrl(SESSION_INVALID_PATH)
        .sessionFixation().changeSessionId()
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
