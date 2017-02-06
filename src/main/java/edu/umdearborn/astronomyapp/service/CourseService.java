package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.Module;

public interface CourseService {

  public CourseUser getCourseUser(String email, String courseId);

  public List<Course> getCourses(String email, boolean hideClosed, boolean hideOpenSoon);

  public List<Module> getModules(String courseId, boolean showVisibleOnly);

  public Course createCourse(Course course);

  public Course updateCourse(Course course);

  public List<CourseUser> getClassList(String courseId, List<CourseUser.CourseRole> roles);

}
