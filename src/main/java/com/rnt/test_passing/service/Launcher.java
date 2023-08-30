package com.rnt.test_passing.service;

import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component("launcher")
public class Launcher {
  private final MessageSource messageSource;
  private final TestExecutor testExecutor;
  private final QuestionService questionService;
  private final IOService ioService;
  private final TestService testService;

  public Launcher(MessageSource messageSource, TestExecutor testExecutor, QuestionService questionService, IOService ioService,
      TestService testService) {
    this.messageSource = messageSource;
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
      ioService.printText(messageSource.getMessage("app.error", null,
          new Locale("ru")));
    }
  }

  private String printAndAskTestNames() {
    ioService.printText(messageSource.getMessage("app.greetings", null,
        new Locale("ru")));
    testService.getTestNames().forEach(ioService::printText);
    return ioService.readText();
  }
}
