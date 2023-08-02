package org.main.service.impl;

import java.util.List;
import org.main.entity.Question;
import org.main.exception.TestReadingException;
import org.main.repo.QuestionRepository;
import org.main.service.IOService;
import org.main.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {
  private final IOService ioService;
  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(IOServiceImpl ioService, QuestionRepository questionRepository) {
    this.ioService = ioService;
    this.questionRepository = questionRepository;
  }

  public List<Question> getQuestions() {
    List<Question> questions = null;
    try {
      ioService.printText("Enter the name of the test: ");
      questionRepository.getTestNames().forEach(ioService::printText);
      String testName = ioService.nextString();
      questions = questionRepository.findQuestionsByName(testName);
    } catch (TestReadingException e) {
      ioService.printText("There is no such test");
    }

    return questions;
  }

}
