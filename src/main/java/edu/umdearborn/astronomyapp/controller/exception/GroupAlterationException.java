package edu.umdearborn.astronomyapp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GroupAlterationException extends RuntimeException {

  private static final long serialVersionUID = 4307750085846741334L;

  public GroupAlterationException(String message) {
    super(message);
  }

}
