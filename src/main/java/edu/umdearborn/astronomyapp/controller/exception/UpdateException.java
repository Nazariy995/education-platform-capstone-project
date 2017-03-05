package edu.umdearborn.astronomyapp.controller.exception;

public class UpdateException extends RuntimeException {

  private static final long serialVersionUID = -7822983110056957517L;

  public UpdateException(String message) {
    super(message);
  }
}
