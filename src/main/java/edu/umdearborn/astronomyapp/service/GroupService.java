package edu.umdearborn.astronomyapp.service;

import java.util.List;
import java.util.Map;

import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;

public interface GroupService {

  public List<CourseUser> joinGroup(String courseUserId, String moduleId, String groupId);

  public ModuleGroup createGroup(String courseUserId, String moduleId);

  public boolean isInAGroup(String courseUserId, String moduleId);

  public List<CourseUser> getUsersInGroup(String groupId);

  public ModuleGroup getGroup(String courseUserId, String moduleId);

  public CourseUser checkin(String email, String password, String groupId);

  public boolean hasLock(String groupId, List<String> checkedIn);

  public Map<String, String> saveAnswers(Map<String, String> answers, String groupId);

}
