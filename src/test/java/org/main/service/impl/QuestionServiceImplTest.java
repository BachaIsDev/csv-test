package org.main.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.main.entity.OptionCatalogue;
import org.main.entity.Question;
import org.main.exception.TestReadingException;
import org.main.repo.QuestionRepository;
import org.main.service.IOService;
import org.main.service.QuestionService;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

  @Test
  void getQuestions_shouldCallRepo() throws TestReadingException {
    QuestionRepository questionRepository = mock(QuestionRepository.class);
    IOService ioService = new IOServiceImpl();
    QuestionService questionService = new QuestionServiceImpl(ioService, questionRepository);
    Question question1 = new Question("issue1", new OptionCatalogue());
    Question question2 = new Question("issue2", new OptionCatalogue());
    List<Question> questions = List.of(question1, question2);
    given(questionRepository.findQuestionsByName("test"))
        .willReturn(questions);

    List<Question> resultQuestions = questionService.getQuestions("test");

    assertEquals(resultQuestions.get(0), question1);
    assertEquals(resultQuestions.get(1), question2);
    verify(questionRepository).findQuestionsByName("test");
  }

}