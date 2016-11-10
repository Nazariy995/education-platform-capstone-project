package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.NumericQuestion;

@Repository
public interface NumericQuestionRepository extends JpaRepository<NumericQuestion, String> {

}
