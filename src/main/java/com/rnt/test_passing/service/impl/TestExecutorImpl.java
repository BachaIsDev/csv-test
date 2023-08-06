package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.service.TestExecutor;
import com.rnt.test_passing.util.impl.QuestionConverter;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.Result;
import com.rnt.test_passing.service.IOService;
import java.util.stream.Collectors;

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
      boolean rightAnswer = processTest(question);
      result.applyAnswer(rightAnswer);
    }

    showResult(result);
  }

  private boolean processTest(Question question) {
    List<String> optionList = showQuestion(question);
    int rightAnswerIndex = 0;

    for(int i = 0; i < optionList.size(); i++){
      if(optionList.get(i).endsWith("true")){
        optionList.set(i, optionList.get(i).split(",")[0]);
        rightAnswerIndex = i + 1;
      }
    }

    int actualAnswer = ioService.readIntByInterval(question.getOptions().size());

    return actualAnswer == rightAnswerIndex;
  }

  private void showResult(Result result){
    ioService.printText(
        ((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
            + "% correctly");
  }

  private List<String> showQuestion(Question question){
    List<String> answersAsList = converter.convert(question);
    List<String> answersToShow = answersAsList.stream()
        .map(answer -> {
          if(answer.endsWith("true")){
            return  answer.split(",")[0];
          }
          return answer;
        })
        .toList();
    ioService.printText(question.getIssue());
    ioService.printText("Answers:");
    answersToShow.forEach(ioService::printText);
    ioService.printText("Enter an answer: ");

    return answersAsList;
  }

}
