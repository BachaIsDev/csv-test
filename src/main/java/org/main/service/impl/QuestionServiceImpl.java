package org.main.service.impl;

import java.util.List;
import org.main.entity.Question;
import org.main.exception.TestReadingException;
import org.main.repo.QuestionRepository;
import org.main.service.IOService;
import org.main.service.QuestionService;
import org.main.util.PathProvider;

public class QuestionServiceImpl implements QuestionService {
  private final IOService ioService;
  private final PathProvider pathProvider;
  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(IOServiceImpl ioService, PathProvider pathProvider,
      QuestionRepository questionRepository) {
    this.ioService = ioService;
    this.pathProvider = pathProvider;
    this.questionRepository = questionRepository;
  }

  public List<Question> getQuestions() {
    List<Question> questions = null;
    try {
      ioService.printText("Enter the name of the test: ");
      pathProvider.getAllTestNames().forEach(ioService::printText);
      String testName = ioService.nextString();
      questions = questionRepository.findQuestionsByName(pathProvider.getPath(testName));
    } catch (TestReadingException e) {
      ioService.printText("There is no such test");
    }

    return questions;
  }

}
