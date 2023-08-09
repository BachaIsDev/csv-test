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
    try {
      String testName = printAndAsTestNames();
      List<Question> questionList = questionService.getQuestions(testName);
      testExecutor.startTest(questionList);
    } catch (TestReadingException | NullPointerException e) {
      ioService.printText("There is no such test");
    }
  }

  private String printAndAsTestNames() {
    ioService.printText("Enter the name of the test: ");
    testService.getTestNames().forEach(ioService::printText);
    return ioService.readText();
  }
}
