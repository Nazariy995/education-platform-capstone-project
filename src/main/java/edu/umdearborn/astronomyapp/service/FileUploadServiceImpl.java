package edu.umdearborn.astronomyapp.service;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class FileUploadServiceImpl implements FileUploadService {

  private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

  @Override
  public String upload(InputStream inutStream, long length, String contentType) {
    return init(inutStream, length, contentType).upload();
  }

  private AwsS3Uploader init(InputStream inutStream, long length, String contentType) {
    return AwsS3Uploader.init(inutStream, length, contentType);
  }

  private static class AwsS3Uploader {

    private AmazonS3    s3            = AmazonS3ClientBuilder.defaultClient();
    private InputStream inputStream;
    private long        contentLength = 0;
    private String      contentType;


    public static AwsS3Uploader init(InputStream inputStream, long length, String contentType) {
      Assert.isTrue(length > 0);
      Assert.isTrue(!StringUtils.isEmpty(contentType));

      AwsS3Uploader uploader = new AwsS3Uploader();
      uploader.inputStream = inputStream;
      uploader.contentLength = length;
      uploader.contentType = contentType;

      logger.info("Created multipart upload request");
      return uploader;
    }

    public String upload() {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(contentLength);
      metadata.setContentType(contentType);

      String fileName = UUID.randomUUID().toString();
      s3.putObject(new PutObjectRequest("astro-app-file", fileName, inputStream, metadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));

      return s3.getUrl("astro-app-file", fileName).toString();
    }

  }

}
