package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.service.QuestionService;
import java.util.ArrayList;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.service.IOService;

public class QuestionServiceImpl implements QuestionService {

  private final IOService ioService;
  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(IOService ioService, QuestionRepository questionRepository) {
    this.ioService = ioService;
    this.questionRepository = questionRepository;
  }

  @Override
  public List<Question> getQuestions(String testName) {
    List<Question> questions = null;
    try {
      questions = questionRepository.findQuestionsByName(testName);
    } catch (TestReadingException e) {
      ioService.printText("There is no such test");
    }

    return questions;
  }

}
