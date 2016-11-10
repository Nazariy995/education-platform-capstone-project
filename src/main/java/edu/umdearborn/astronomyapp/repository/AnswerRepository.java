package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {

}
