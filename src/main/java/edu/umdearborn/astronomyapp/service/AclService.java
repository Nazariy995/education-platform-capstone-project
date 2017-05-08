package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.CourseUser;

public interface AclService {

  public void enforceInCourse(String email, String courseId);

  public void enforceIsCourseRole(String email, String courseId, List<CourseUser.CourseRole> role);

  public void enforceInGroup(String courseUserId, String groupId);

  public void enforceInCourse(String email, String courseId, String courseUserId);

  public void enforceModuleVisible(String moduleId);

  public void enforeceModuleInCourse(String courseId, String moduleId);

  public void enforceGroupInCourse(String groupId, String courseId);

  public void enforceHasLock(String groupId, List<String> checkedIn);

  public void enforceGroupLocked(String groupId, boolean shouldBeLocked);

  public void enforceModuleOpen(String moduleId);

  public void enforceHasRoleInCourse(String courseUserId, String courseId,
      List<CourseUser.CourseRole> role);

  public void enforceCourseNotOpen(String courseId);

  public void enforceModuleNotOpen(String moduleId);

  public void enforceModuleClosed(String moduleId);
  
  public void enforeModuleNotClosed(String moduleId);
  
  public void enforeceCourseNotClosed(String courseId);

}
