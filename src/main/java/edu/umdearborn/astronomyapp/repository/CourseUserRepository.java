package edu.umdearborn.astronomyapp.repository;

import java.util.Set;

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
}
