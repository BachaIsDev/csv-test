package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.service.TestExecutor;
import com.rnt.test_passing.util.QuestionConverter;
import java.util.Collections;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.Result;
import com.rnt.test_passing.service.IOService;

public class TestExecutorImpl implements TestExecutor {
  private final IOService ioService;
  private final QuestionConverter converter;

  public TestExecutorImpl(IOService ioService, QuestionConverter converter) {
    this.ioService = ioService;
    this.converter = converter;
  }

  @Override
  public void startTest(List<Question> questions) {
    ioService.printText("Answer correctly as many questions as possible. Good Luck!");
    Result result = new Result(0, questions.size());
    for (Question question : questions) {
      boolean rightAnswer = processQuestion(question);
      result.applyAnswer(rightAnswer);
    }

    showResult(result);
  }

  private boolean processQuestion(Question question) {
    Collections.shuffle(question.getOptions());
    showOptions(question);

    int rightAnswerIndex = 0;
    int actualAnswer =
        ioService.readIntByInterval(question.getOptions().size(), "There is no such option");
    List<Option> options = question.getOptions();
    for(int i = 0; i < options.size(); i++){
      if(options.get(i).isCorrect()){
        rightAnswerIndex = i + 1;
      }
    }
    return actualAnswer == rightAnswerIndex;
  }

  private void showResult(Result result){
    ioService.printText(
        ((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
            + "% correctly");
  }

  private void showOptions(Question question){
    String answersAsList = converter.convert(question);

    ioService.printText(question.getIssue());
    ioService.printText("Answers:");
    ioService.printText(answersAsList);
    ioService.printText("Enter an answer: ");
  }

}
