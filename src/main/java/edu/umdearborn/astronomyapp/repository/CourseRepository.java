package edu.umdearborn.astronomyapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.Module;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

  @Query("select m from Module m join m.course c where c.id = :courseId "
      + "and m.visibleTimestamp <= current_timestamp()")
  public List<Module> getVisibleModules(@Param(value = "courseId") String courseId);

  @Query("select m from Module m join m.course c where c.id = :courseId")
  public List<Module> getAllModules(@Param(value = "courseId") String courseId);

}
