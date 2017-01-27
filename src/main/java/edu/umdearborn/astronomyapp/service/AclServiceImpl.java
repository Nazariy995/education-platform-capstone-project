package edu.umdearborn.astronomyapp.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import edu.umdearborn.astronomyapp.repository.CourseUserRepository;

@Service
public class AclServiceImpl implements AclService {

  private static final Logger logger = LoggerFactory.getLogger(AclServiceImpl.class);

  private CourseUserRepository courseUserRepository;

  public AclServiceImpl(CourseUserRepository courseUserRepository) {
    this.courseUserRepository = courseUserRepository;
  }

  @Override
  public void enforceCourse(String email, String courseId) {
    if (StringUtils.isEmpty(StringUtils.trimToEmpty(courseId))
        || !courseUserRepository.isInCourse(email, courseId)) {
      logger.debug("{} does not have access to course: {}", email, courseId);
      throw new AccessDeniedException(email + " cannot access " + courseId);
    }

    logger.debug("{} has access to course: {}", email, courseId);
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(courseUserRepository);
  }

}
