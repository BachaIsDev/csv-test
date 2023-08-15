package com.rnt.test_passing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.service.impl.IOServiceImpl;
import com.rnt.test_passing.service.impl.QuestionServiceImpl;
import com.rnt.test_passing.service.impl.TestExecutorImpl;
import com.rnt.test_passing.service.impl.TestServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class LauncherTest {
  @InjectMocks
  private Launcher launcher;

  @Mock
  private TestExecutorImpl testExecutor;
  @Mock
  private TestServiceImpl testService;
  @Mock
  private IOServiceImpl ioService;
  @Mock
  private QuestionServiceImpl questionService;

  @Test
  void launchTest_shouldCallExecutor() {
    List<Option> options1 = List.of(new Option("test1-1", true),
        new Option("test1-2", false));
    List<Option> options2 = List.of(new Option("test2-1", true),
        new Option("test2-2", false));
    List<Question> questions = List.of(new Question("text1", options1),
        new Question("text2", options2));
    given(questionService.getQuestions(any())).willReturn(questions);

    launcher.launchTest();

    verify(testExecutor).startTest(questions);
  }
  @Test
  void launchTest_shouldNotCallExecutor() {
    launcher.launchTest();

    verify(testExecutor, never()).startTest(any());
  }

  @Test
  void launchTest_shouldCallTestService() {
    launcher.launchTest();

    verify(testService).getTestNames();
  }

  @Test
  void launchTest_exceptionShouldBeHandled() {
    doThrow(new TestReadingException("test thrown"))
        .when(testExecutor).startTest(any());

    assertThatCode(() -> launcher.launchTest()).doesNotThrowAnyException();
  }
}