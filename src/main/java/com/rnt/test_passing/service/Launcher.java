package com.rnt.test_passing.service;

import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("launcher")
public class Launcher {
  private final TestExecutor testExecutor;
  private final QuestionService questionService;
  private final IOService ioService;
  private final TestService testService;

  public Launcher(TestExecutor testExecutor, QuestionService questionService, IOService ioService,
      TestService testService) {
    this.testExecutor = testExecutor;
    this.questionService = questionService;
    this.ioService = ioService;
    this.testService = testService;
  }

  public void launchTest() {
    try {
      String testName = printAndAskTestNames();
      List<Question> questionList = questionService.getQuestions(testName);
      testExecutor.startTest(questionList);
    } catch (TestReadingException e) {
      ioService.printText("There is no such test");
    }
  }

  private String printAndAskTestNames() {
    ioService.printText("Enter the name of the test: ");
    testService.getTestNames().forEach(ioService::printText);
    return ioService.readText();
  }
}
