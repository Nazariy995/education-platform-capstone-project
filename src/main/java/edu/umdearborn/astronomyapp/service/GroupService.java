package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;

public interface GroupService {

  public List<CourseUser> joinGroup(String courseUserId, String moduleId, String groupId);

  public ModuleGroup createGroup(String courseUserId, String moduleId);

  public boolean isInAGroup(String courseUserId, String moduleId);

  public List<CourseUser> getUsersInGroup(String groupId);

}
