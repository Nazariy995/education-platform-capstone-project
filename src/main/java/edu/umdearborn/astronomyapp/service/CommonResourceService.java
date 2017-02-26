package edu.umdearborn.astronomyapp.service;

import java.util.Map;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;

public interface CommonResourceService {

  public AstroAppUser findByEmail(String email);
  public Map<String, String> getCourseUserSummary(String email);
}
