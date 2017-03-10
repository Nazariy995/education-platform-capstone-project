package edu.umdearborn.astronomyapp.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.util.MimeType;

public interface CsvParserService {

  public static enum Header {
    EMAIL, FIRST_NAME, LAST_NAME
  }

  public List<String[]> parseClassList(InputStream stream, MimeType type);
}
