package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;

public interface CourseService {

  public CourseUser getCourseUser(String email, String courseId);

  public List<Course> getCourses(String email, boolean hideClosed, boolean hideOpenSoon);

  public Course createCourse(Course course, String email);

  public Course updateCourse(Course course);

  public List<CourseUser> getClassList(String courseId, List<CourseUser.CourseRole> roles);

  public Course getCourseDetails(String courseId);

  public Course clone(Course course, String cloneFromId, String email);

  public List<Course> getCourses();

  public Course getCourse(String id);
  
  public void deleteCourse(String id);

}
