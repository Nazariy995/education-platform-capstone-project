package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;

public interface CourseService {

  public CourseUser getCourseUser(String email, String courseId);

  public List<Course> getCourses(String email, boolean hideClosed, boolean hideOpenSoon);

  public Course createCourse(Course course);

  public Course updateCourse(Course course);

  public List<CourseUser> getClassList(String courseId, List<CourseUser.CourseRole> roles);

  public Course getCourseDetails(String courseId);

}
