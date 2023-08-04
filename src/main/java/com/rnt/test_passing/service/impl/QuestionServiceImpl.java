package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.service.QuestionService;
import java.util.ArrayList;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.service.IOService;

public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public List<Question> getQuestions(String testName) {
    List<Question> questions = null;
    try {
      questions = questionRepository.findQuestionsByName(testName);
    } catch (TestReadingException e) {
      throw new TestReadingException("No such test", e);
    }

    return questions;
  }

}
