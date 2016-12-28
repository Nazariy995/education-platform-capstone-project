package edu.umdearborn.astronomyapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

  private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

  @RequestMapping(value = "/{[path:[^\\.]*}")
  public String redirect() {
    logger.debug("Forewarding to static resource...");
    return "forward:/";
  }

}
