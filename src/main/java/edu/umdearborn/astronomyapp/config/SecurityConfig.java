package edu.umdearborn.astronomyapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.umdearborn.astronomyapp.config.annotation.Dev;
import edu.umdearborn.astronomyapp.config.annotation.Prod;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .formLogin()
        .defaultSuccessUrl("/home", false)
        .usernameParameter("email")
        .failureUrl("/login?error")
        .loginProcessingUrl("/login")
        .permitAll()
        .and()
      .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .and()
      .authorizeRequests()
        .antMatchers("/", "/home", "/static/**", "/public/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/instructor/**").hasRole("INSTRUCTOR")
        .anyRequest().fullyAuthenticated()
        .and()
      .sessionManagement()
        .invalidSessionUrl("/login?invalid-session")
        .sessionFixation().changeSessionId()
        .sessionFixation().newSession()
        .maximumSessions(1)
        .expiredUrl("/login?session-expired");
    // @formatter:on
  }

  @Prod
  @Bean("passwordEncoder")
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Dev
  @Bean("passwordEncoder")
  public PasswordEncoder noOpPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}
