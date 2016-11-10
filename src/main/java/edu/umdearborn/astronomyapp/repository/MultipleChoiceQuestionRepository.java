package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;

@Repository
public interface MultipleChoiceQuestionRepository
    extends JpaRepository<MultipleChoiceQuestion, String> {

}
