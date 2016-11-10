package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {

}
