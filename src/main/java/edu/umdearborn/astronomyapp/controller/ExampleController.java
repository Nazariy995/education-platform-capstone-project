package edu.umdearborn.astronomyapp.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

  @RequestMapping("/admin")
  public String helloAdmin(Principal principal) {
    return "Hello, " + principal.getName();
  }

  @RequestMapping({"/", "/home"})
  public String hello() {
    return "Homepage!";
  }
}
