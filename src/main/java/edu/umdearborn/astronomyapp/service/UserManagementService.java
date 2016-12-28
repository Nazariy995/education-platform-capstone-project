package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;

public interface UserManagementService {

  public List<CourseUser> addUsersToCourse(Course course, CourseUser... users);

  public List<CourseUser> addUsersToCourse(String courseId, CourseUser... users);

  public AstroAppUser persistNewUser(AstroAppUser user);

  public AstroAppUser updateUser(AstroAppUser user);

}
