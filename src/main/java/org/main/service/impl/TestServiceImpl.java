package org.main.service.impl;

import java.util.List;
import org.main.entity.Result;
import org.main.entity.Question;
import org.main.service.TestService;
import org.main.util.PathProvider;

public class TestServiceImpl implements TestService {
  private final IOServiceImpl ioServiceImpl;
  private final PathProvider pathProvider;

  public TestServiceImpl(IOServiceImpl ioServiceImpl, PathProvider pathProvider) {
    this.ioServiceImpl = ioServiceImpl;
    this.pathProvider = pathProvider;
  }

  @Override
  public Result startTest(List<Question> testList){
    ioServiceImpl.greetings();
    Result result = new Result(0, testList.size());
    for(Question test: testList){
      boolean rightAnswer = ioServiceImpl.askQuestion(test);
      if(rightAnswer){
        result.setRightAnswers(result.getRightAnswers() + 1);
      }
    }

    return result;
  }

  @Override
  public String getTestPath(String fileName){
    return pathProvider.getPath(fileName);
  }

}
