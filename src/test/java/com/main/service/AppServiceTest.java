package com.main.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;
import com.rnt.test_passing.repo.QuestionRepository;
import com.rnt.test_passing.service.AppService;
import com.rnt.test_passing.service.IOService;
import com.rnt.test_passing.service.QuestionService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppServiceTest {
  @InjectMocks
  private AppService appService;
  @Mock
  private QuestionRepository questionRepository;
  @Mock
  private QuestionService questionService;
  @Mock
  private IOService ioService;
  @Mock
  private Question question;


  @Test
  void launch_shouldCallProcessTestFromCsv_whenSystemInIs1() throws TestReadingException {
    String data = "1";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    appService.launchTest();
    System.setIn(stdin);

    verify(questionRepository).findQuestionsByTestName(any());
  }
}