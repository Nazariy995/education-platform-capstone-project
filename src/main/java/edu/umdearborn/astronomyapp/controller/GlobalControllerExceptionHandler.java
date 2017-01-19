package edu.umdearborn.astronomyapp.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.umdearborn.astronomyapp.controller.exception.ValidationErrorsException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  private static final Logger logger =
      LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  private static String getUrl(HttpServletRequest request) {
    return request.getRequestURL()
        .append((request.getQueryString() != null ? "?" + request.getQueryString() : ""))
        .toString();
  }

  @ExceptionHandler(ValidationErrorsException.class)
  @ResponseStatus(code = BAD_REQUEST, reason = "Request was not valid")
  public Errors handleBindingResultError(HttpServletRequest request,
      ValidationErrorsException error) {
    String errorDetails = error.getResult().getFieldErrors().parallelStream()
        .map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.joining(", "));
    logger.error("Validation failed at {} with details: {}", getUrl(request), errorDetails);

    return error.getResult();
  }

}
