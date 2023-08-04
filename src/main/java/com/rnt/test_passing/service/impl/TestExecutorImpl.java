package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.service.TestExecutor;
import java.util.List;
import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.Result;
import com.rnt.test_passing.service.IOService;
import com.rnt.test_passing.util.QuestionConverter;

public class TestExecutorImpl implements TestExecutor {

  private final IOService ioService;
  private final QuestionConverter converter;

  public TestExecutorImpl(IOServiceImpl ioService, QuestionConverter converter) {
    this.ioService = ioService;
    this.converter = converter;
  }

  @Override
  public void startTest(List<Question> questions) {
    ioService.printText("Answer correctly as many questions as possible. Good Luck!");
    Result result = new Result(0, questions.size());
    for (Question question : questions) {
      boolean rightAnswer = processTest(question);
      result.applyAnswer(rightAnswer);
    }

    showResult(result);
  }

  private boolean processTest(Question question) {
    Boolean isCorrect;
    showQuestion(question);

    do {
      isCorrect = false;
      String option = ioService.readText();
      if (option != null) {
        List<String> options = question.getOptions().stream().map(Option::getText).toList();

        if (options.contains(option)) {
          for (Option opt : question.getOptions()) {
            if (opt.isCorrect() && opt.getText().equals(option)) {
              isCorrect = true;
              break;
            }
          }
        } else {
          isCorrect = null;
        }

        if (isCorrect == null) {
          ioService.printText("There is no such option. Please, try again");
        }
      }
    } while (isCorrect == null);

    return isCorrect;
  }

  private void showResult(Result result){
    ioService.printText(
        ((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
            + "% correctly");
  }

  private void showQuestion(Question question){
    String answersAsString = converter.getAnswersAsString(question);
    ioService.printText(question.getIssue());
    ioService.printText("Answers:");
    ioService.printText(answersAsString);
    ioService.printText("Enter an answer: ");
  }

}
