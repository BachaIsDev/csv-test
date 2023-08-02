package org.main.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.main.entity.Question;
import org.main.exception.TestReadingException;
import org.main.repo.QuestionRepository;
import org.main.service.IOService;
import org.main.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {

  private final IOService ioService;
  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(IOService ioService, QuestionRepository questionRepository) {
    this.ioService = ioService;
    this.questionRepository = questionRepository;
  }

  public List<Question> getQuestions(String testName) {
    List<Question> questions = null;
    try {
      questions = questionRepository.findQuestionsByName(testName);
    } catch (TestReadingException e) {
      ioService.printText("There is no such test");
    }

    return questions;
  }

  @Override
  public List<String> getTestNames(){
    List<String> result = new ArrayList<>();
    try {
      result = questionRepository.getTestNames();
    }catch (TestReadingException e) {
      ioService.printText("There is no such test");
    }
    return result;
  }

}
