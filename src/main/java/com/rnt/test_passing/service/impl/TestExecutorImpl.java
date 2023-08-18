package com.rnt.test_passing.service.impl;

import static java.util.Objects.isNull;

import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.service.TestExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.Result;
import com.rnt.test_passing.service.IOService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TestExecutorImpl implements TestExecutor {
  private final IOService ioService;
  private final ConversionService conversionService;

  public TestExecutorImpl(IOService ioService, ConversionService conversionService) {
    this.ioService = ioService;
    this.conversionService = conversionService;
  }

  @Override
  public void startTest(List<Question> questions) {
    if (isNull(questions)) {
      throw new TestReadingException("There is no any question");
    }
    ioService.printText("Answer correctly as many questions as possible. Good Luck!");
    Result result = new Result(0, questions.size());
    for (Question question : questions) {
      boolean rightAnswer = processQuestion(question);
      result.applyAnswer(rightAnswer);
    }

    showResult(result);
  }

  private boolean processQuestion(Question question) {
    Question shuffledQuestion = shuffleOptionsInQuestion(question);
    showOptions(shuffledQuestion);

    int actualAnswer = ioService.readIntByInterval(shuffledQuestion.getOptions().size(), "There is no such option");
    return shuffledQuestion
        .getOptions()
        .get(actualAnswer - 1)
        .isCorrect();
  }

  private void showResult(Result result){
    ioService.printText(
        ((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
            + "% correctly");
  }

  private void showOptions(Question question){
    String answersAsList = conversionService.convert(question.getOptions(), String.class);

    ioService.printText(question.getIssue());
    ioService.printText("Answers:");
    ioService.printText(answersAsList);
    ioService.printText("Enter an answer: ");
  }

  private Question shuffleOptionsInQuestion(Question question) {
    List<Option> shuffledOptions = new ArrayList<>(question.getOptions());
    Collections.shuffle(shuffledOptions);
    return new Question(question.getIssue(), shuffledOptions);
  }

}
