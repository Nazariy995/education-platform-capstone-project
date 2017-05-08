package edu.umdearborn.astronomyapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
  
  private MailSender mailSender;
  private SimpleMailMessage mailTemplate;
  
  public EmailServiceImpl(MailSender mailSender, SimpleMailMessage mailTemplate) {
    this.mailSender = mailSender;
    this.mailTemplate = mailTemplate;
  }

  @Async
  @Override
  public void send(String to, String subject, String body) throws MailException {
    SimpleMailMessage email = new SimpleMailMessage(mailTemplate);
    email.setTo(to);
    email.setSubject(subject);
    email.setText(body);
    logger.debug("Sending email: {}", email);
    mailSender.send(email);
  }

}
