package org.main.service;

import java.util.List;
import org.main.entity.Question;

public class AppService {

  private final TestService testService;
  private final QuestionService questionService;
  private final IOService ioService;

  public AppService(TestService testService, QuestionService questionService, IOService ioService) {
    this.testService = testService;
    this.questionService = questionService;
    this.ioService = ioService;
  }

  public void launchTest() {
    List<Question> questionList = null;
    ioService.printText("Enter the name of the test: ");
    questionService.getTestNames().forEach(ioService::printText);
    String testName = ioService.nextString();
    questionList = questionService.getQuestions(testName);

    if (questionList == null || questionList.isEmpty()) {
      return;
    }

    testService.startTest(questionList);
  }



}
