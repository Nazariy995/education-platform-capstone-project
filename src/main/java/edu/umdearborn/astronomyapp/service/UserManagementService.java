package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.CourseUser;

public interface UserManagementService {

  public List<CourseUser> addUsersToCourse(String courseId, CourseUser... users);

  public AstroAppUser persistNewUser(AstroAppUser user);

  public AstroAppUser updateUser(AstroAppUser user);

  public boolean emailExists(String email);
  
  public CourseUser updateCourseUserStatus(String courseUserId, boolean isActive);

  public void resetPassword(String email);
  
  public void changePassword(String email, String password);

  public List<AstroAppUser> getAdminInstructorList();

}
