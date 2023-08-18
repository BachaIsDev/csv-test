package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.exception.TestReadingException;
import java.io.InputStream;
import java.io.PrintStream;
import com.rnt.test_passing.service.IOService;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IOServiceImpl implements IOService {

  private final PrintStream printStream;
  private final Scanner scanner;

  public IOServiceImpl(@Value("#{T(java.lang.System).out}") PrintStream printStream,
      @Value("#{T(java.lang.System).in}") InputStream inputStream) {
    this.printStream = printStream;
    this.scanner = new Scanner(inputStream);
  }

  @Override
  public void printText(String text) {
    printStream.println(text);
  }

  @Override
  public String readText() {
    try {
      return scanner.nextLine();
    } catch (NoSuchElementException e) {
      throw new TestReadingException("Error during read text", e);
    }
  }

  @Override
  public int readIntByInterval(int max, String message){
    while (true) {
      int answerNumber = scanner.nextInt();
      if(answerNumber >= 1 && answerNumber <= max) {
        return answerNumber;
      }
      printText(message);
    }
  }
}
