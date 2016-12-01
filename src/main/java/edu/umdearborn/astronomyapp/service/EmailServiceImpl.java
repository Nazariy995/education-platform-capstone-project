package edu.umdearborn.astronomyapp.service;

import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.util.email.EmailContextBuilder;

@Service
public class EmailServiceImpl implements EmailService {

  @Override
  public Map<String, String> buildEmailContext(EmailContextBuilder emailContextBuilder) {
    return emailContextBuilder.buildContext();
  }

  @Async
  @Override
  public void send(Map<String, String> context) throws MailException {
    // TODO Auto-generated method stub

  }

}
