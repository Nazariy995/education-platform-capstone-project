package edu.umdearborn.astronomyapp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    if (Arrays.asList(context.getEnvironment().getActiveProfiles()).parallelStream()
        .anyMatch(p -> p.equalsIgnoreCase("dev"))) {
      logger.warn("Application is running in local development mode");
    }
  }

}
