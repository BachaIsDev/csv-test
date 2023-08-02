package org.main.service;

import java.util.List;
import org.main.entity.Result;
import org.main.entity.Question;

public class AppService {
  private final TestService testService;
  private final QuestionService questionService;

  public AppService(TestService testService, QuestionService questionService) {
    this.testService = testService;
    this.questionService = questionService;
  }

  public void launchTest() {
    List<Question> questionList = null;
    questionList = questionService.getQuestions();

    if (questionList == null || questionList.isEmpty()) {
      return;
    }

    testService.startTest(questionList);

  }
}
