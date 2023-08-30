package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.service.TestService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
  private final QuestionRepository questionRepository;

  public TestServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public List<String> getTestNames() throws TestReadingException {
      return questionRepository.getQuestionTestNames();
  }
}
