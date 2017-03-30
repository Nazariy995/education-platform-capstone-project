package edu.umdearborn.astronomyapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class GeneralConfig {

  @Bean
  public SimpleMailMessage mailTemplate(
      @Value("${email.template.from:astro@umich.edu}") String from) {
    SimpleMailMessage mailTempalte = new SimpleMailMessage();
    mailTempalte.setFrom(from);
    return mailTempalte;
  }
}
