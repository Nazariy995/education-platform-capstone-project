package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.CourseUser;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, String> {

}
