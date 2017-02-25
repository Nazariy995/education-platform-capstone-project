package edu.umdearborn.astronomyapp.service;

import java.io.InputStream;

public interface FileUploadService {

  public String upload(InputStream inputStream, long length, String contentType);
}
