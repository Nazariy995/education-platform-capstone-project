package edu.umdearborn.astronomyapp.service;

import org.springframework.mail.MailException;

public interface EmailService {

  public void send(String to, String subject, String body) throws MailException;
}
