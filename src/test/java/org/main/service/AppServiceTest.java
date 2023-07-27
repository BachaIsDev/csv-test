package org.main.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.main.entity.Question;
import org.main.exception.TestException;
import org.main.repo.TestRepository;
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
  private TestRepository testRepository;
  @Mock
  private TestService testService;
  @Mock
  private IOService ioService;
  @Mock
  private Question question;


  @Test
  void launch_shouldCallProcessTestFromCsv_whenSystemInIs1() throws TestException {
    String data = "1";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    appService.launchTest();
    System.setIn(stdin);

    verify(testRepository).getQuestions(any());
  }
}