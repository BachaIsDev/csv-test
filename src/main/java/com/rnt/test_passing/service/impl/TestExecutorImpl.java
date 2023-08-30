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
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TestExecutorImpl implements TestExecutor {
  private final MessageSource messageSource;
  private final IOService ioService;
  private final ConversionService conversionService;

  public TestExecutorImpl(MessageSource messageSource, IOService ioService, ConversionService conversionService) {
    this.messageSource = messageSource;
    this.ioService = ioService;
    this.conversionService = conversionService;
  }

  @Override
  public void startTest(List<Question> questions) {
    if (isNull(questions)) {
      throw new TestReadingException("There is no any question");
    }
    ioService.printText(messageSource.getMessage("app.good_luck", null, new Locale("ru")));
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

    int actualAnswer = ioService.readIntByInterval(shuffledQuestion.getOptions().size(),
        messageSource.getMessage("app.no_opt", null, new Locale("ru")));
    return shuffledQuestion
        .getOptions()
        .get(actualAnswer - 1)
        .isCorrect();
  }

  private void showResult(Result result){
    ioService.printText(
        ((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
            + messageSource.getMessage("app.result", null, new Locale("ru")));
  }

  private void showOptions(Question question){
    String answersAsList = conversionService.convert(question.getOptions(), String.class);

    ioService.printText(question.getIssue());
    ioService.printText(messageSource.getMessage("app.answers", null, new Locale("ru")));
    ioService.printText(answersAsList);
    ioService.printText(messageSource.getMessage("app.enter_answer", null, new Locale("ru")));
  }

  private Question shuffleOptionsInQuestion(Question question) {
    List<Option> shuffledOptions = new ArrayList<>(question.getOptions());
    Collections.shuffle(shuffledOptions);
    return new Question(question.getIssue(), shuffledOptions);
  }

}
