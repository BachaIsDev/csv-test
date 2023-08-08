package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.service.QuestionService;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;

public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public List<Question> getQuestions(String testName) {
    try {
      return questionRepository.findQuestionsByTestName(testName);
    } catch (TestReadingException e) {
      throw new TestReadingException("No such test", e);
    }
  }
}
