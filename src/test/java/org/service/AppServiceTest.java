package org.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppServiceTest {

  @Mock
  CsvProcessor csvProcessor;
  @Mock
  TestProcessor testProcessor;
  @Mock
  DirectoryHandler directoryHandler;

  @Mock
  org.entity.Test test;


  @Test
  void launch_shouldCallProcessTestFromCsv_whenSystemInIs1(){
    AppService appService = new AppService(csvProcessor, testProcessor, directoryHandler);
    String data = "1";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    appService.launch();
    System.setIn(stdin);

    verify(csvProcessor).processTest(any());
  }
}