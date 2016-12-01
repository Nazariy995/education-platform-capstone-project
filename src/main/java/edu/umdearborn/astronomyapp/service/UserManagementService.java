package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;

public interface UserManagementService {

  public AstroAppUser updateUser(AstroAppUser user);

  public List<CourseUser> addUsersToCourse(Course course, CourseUser... users);

  public List<CourseUser> addUsersToCourse(String courseId, CourseUser... users);
  
  public AstroAppUser persistNewUser(AstroAppUser user);

}
