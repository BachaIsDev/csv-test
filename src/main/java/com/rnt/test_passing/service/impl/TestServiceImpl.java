package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.service.TestService;
import java.util.List;

public class TestServiceImpl implements TestService {
  private final QuestionRepository questionRepository;

  public TestServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public List<String> getTestNames() {
    try {
      return questionRepository.getQuestionTestNames();
    } catch (TestReadingException e) {
      throw new TestReadingException("There is no such test", e);
    }
  }
}
