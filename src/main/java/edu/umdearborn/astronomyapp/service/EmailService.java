package edu.umdearborn.astronomyapp.service;

import java.util.Map;

import org.springframework.mail.MailException;

import edu.umdearborn.astronomyapp.util.email.EmailContextBuilder;

public interface EmailService {

  public Map<String, String> buildEmailContext(EmailContextBuilder emailContextBuilder);

  public void send(Map<String, String> context) throws MailException;
}
