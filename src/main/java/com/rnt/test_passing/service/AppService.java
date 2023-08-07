package com.rnt.test_passing.service;

import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import java.util.List;

public class AppService {

  private final TestExecutor testExecutor;
  private final QuestionService questionService;
  private final IOService ioService;
  private final TestService testService;

  public AppService(TestExecutor testExecutor, QuestionService questionService, IOService ioService,
      TestService testService) {
    this.testExecutor = testExecutor;
    this.questionService = questionService;
    this.ioService = ioService;
    this.testService = testService;
  }

  public void launchTest() {
    List<Question> questionList = null;
    try {
      ioService.printText("Enter the name of the test: ");
      testService.getTestNames().forEach(ioService::printText);
      String testName = ioService.readText();

      questionList = questionService.getQuestions(testName);
    } catch (TestReadingException e){
      ioService.printText("There is no such test");
    }

    if (questionList == null || questionList.isEmpty()) {
      return;
    }

    testExecutor.startTest(questionList);
  }



}
