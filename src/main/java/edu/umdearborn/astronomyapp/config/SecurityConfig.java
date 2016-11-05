package edu.umdearborn.astronomyapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
//      Uncomment when running locally with H2 database and web console
//      !!  When running H2, ensure you uncomment <scope>test<scope> from   !!
//      !!  the H2 dependency in pom.xml                                    !!
//      .headers()
//        .frameOptions().disable().and()
      .cors()
        .and()
      .csrf()
        .disable()
      .requiresChannel()
        .anyRequest()
        .requiresInsecure() // TODO: Setup HTTPS
        .and()
      .formLogin()
        .defaultSuccessUrl("/home", false)
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
        .antMatchers("/", "/home", "/static/**", "/h2-console/**").permitAll()
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
