package edu.umdearborn.astronomyapp.service;

import java.util.List;

import edu.umdearborn.astronomyapp.entity.Question;

public interface AutoGradeService {

  public List<Question> getGatekeepers(String moduleId, int pageNum);
  
  public boolean checkAnswer(String answerId);
}
