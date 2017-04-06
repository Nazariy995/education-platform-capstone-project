package edu.umdearborn.astronomyapp.service;

import java.math.BigDecimal;
import java.util.List;

import edu.umdearborn.astronomyapp.entity.Answer;
import edu.umdearborn.astronomyapp.entity.Question;

public interface AutoGradeService {

  public List<Question> getGatekeepers(String moduleId, int pageNum);

  public boolean checkAnswer(String answerId);

  public boolean answeredGatekeepers(String moduleId, int pageNum, String groupId);

  public Answer setPointsEarned(String answerId, BigDecimal points);
}
