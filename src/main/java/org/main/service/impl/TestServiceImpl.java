package org.main.service.impl;

import java.util.List;
import org.main.entity.Question;
import org.main.entity.Result;
import org.main.service.IOService;
import org.main.service.TestService;

public class TestServiceImpl implements TestService {

  private final IOService ioService;

  public TestServiceImpl(IOServiceImpl ioService) {
    this.ioService = ioService;
  }

  @Override
  public void startTest(List<Question> testList) {
    ioService.printText("Answer correctly as many questions as possible. Good Luck!");
    Result result = new Result(0, testList.size());
    for (Question test : testList) {
      boolean rightAnswer = processTest(test);
      if (rightAnswer) {
        result.setRightAnswers(result.getRightAnswers() + 1);
      }
    }

    ioService.printText(
        ((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
            + "% correctly");
  }

  private boolean processTest(Question question) {
    Boolean isCorrect;
    String answersAsString = getAnswersAsString(question);
    ioService.printText(question.getIssue());
    ioService.printText("Answers:");
    ioService.printText(answersAsString);
    ioService.printText("Enter an answer: ");

    do {
      isCorrect = false;
      String option = ioService.nextString();
      if (option != null) {
        isCorrect = question.getOption().getOptions().getOrDefault(option, null);
        if (isCorrect == null) {
          ioService.printText("There is no such option. Please, try again");
        }
      }
    } while (isCorrect == null);

    return isCorrect;
  }

  private String getAnswersAsString(Question question) {
    String answersAsString = "";
    for (String answer : question.getOption().getOptions().keySet()) {
      answersAsString = answersAsString + answer + "\n";
    }

    return answersAsString;
  }

}
