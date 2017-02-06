package edu.umdearborn.astronomyapp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, String> {

  @Query("select c from CourseUser cu join cu.user u join cu.course c "
      + "where lower(u.email) = lower(:email) and "
      + "c.openTimestamp <= current_timestamp() and c.closeTimestamp >= current_timestamp()")
  public Set<Course> getCurrentCourses(@Param("email") String email);

  @Query("select count(cu) > 0 from CourseUser cu join cu.user u join cu.course c "
      + "where lower(u.email) = lower(:email) and lower(c.id) = lower(:courseId) "
      + "and cu.isActive = true")
  public boolean isInCourse(@Param("email") String email, @Param("courseId") String courseId);

  @EntityGraph(attributePaths = {"user"}, type = EntityGraphType.LOAD)
  @Query("select cu from CourseUser cu join cu.user u join cu.course c "
      + "where c.id = :courseId and cu.isActive = true and u.isEnabled = true")
  public List<CourseUser> getClassList(@Param(value = "courseId") String courseId);

  @Query("select cu from CourseUser cu join cu.user u join cu.course c "
      + "where c.id = :courseId and cu.isActive = true and u.isEnabled = true and "
      + "lower(u.email) = lower(:email)")
  public CourseUser getCourseUser(@Param(value = "courseId") String courseId,
      @Param(value = "email") String email);

  @Query("select cu.role from CourseUser cu join cu.user u join cu.course c "
      + "where c.id = :courseId and cu.isActive = true and u.isEnabled = true "
      + "and lower(u.email) = lower(:email)")
  public CourseUser.CourseRole getRole(@Param(value = "courseId") String courseId,
      @Param(value = "email") String email);

}
