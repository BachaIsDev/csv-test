package org.main.service;

import java.util.List;
import org.main.exception.TestException;
import org.main.repo.TestRepository;
import org.main.entity.Result;
import org.main.entity.Question;

public class AppService {
  private final TestRepository testRepository;
  private final TestService testService;
  private final IOService ioService;

  public AppService(TestRepository testRepository, TestService testService, IOService ioService) {
    this.testRepository = testRepository;
    this.testService = testService;
    this.ioService = ioService;
  }

  public void launchTest() {

    List<Question> questionList = null;
    try {
      questionList = testRepository.getQuestions(testService.getTestPath(ioService.askFileName()));
      if(questionList.isEmpty()){
        return;
      }
    } catch (TestException e) {
      System.out.println("Format of question is wrong!");;
    }
    Result result = testService.startTest(questionList);

    ioService.getResult(result);
  }
}
