package com.main.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.rnt.test_passing.repo.impl.QuestionRepositoryImpl;
import com.rnt.test_passing.service.impl.QuestionServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
  @InjectMocks
  private QuestionServiceImpl questionService;

  @Mock
  private QuestionRepositoryImpl questionRepository;

  @Test
  void getQuestions_shouldCallRepo() throws TestReadingException {
    List<Option> options1 = List.of(new Option("test1-1", true),
        new Option("test1-2", false));
    List<Option> options2 = List.of(new Option("test2-1", true),
        new Option("test2-2", false));
    Question question1 = new Question("text1", options1);
    Question question2 = new Question("text2", options2);
    List<Question> questions = List.of(question1, question2);
    given(questionRepository.findQuestionsByTestName("test"))
        .willReturn(questions);

    List<Question> resultQuestions = questionService.getQuestions("test");

    assertThat(resultQuestions).containsExactlyElementsOf(questions);
    verify(questionRepository).findQuestionsByTestName("test");
  }

}