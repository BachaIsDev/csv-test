package com.rnt.test_passing.service.impl;

import com.rnt.test_passing.exception.TestReadingException;
import java.io.PrintStream;
import com.rnt.test_passing.service.IOService;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
  private final PrintStream printStream;
  private final Scanner scanner;

  public IOServiceImpl(PrintStream printStream, Scanner scanner) {
    this.printStream = printStream;
    this.scanner = scanner;
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
    int answerNumber = scanner.nextInt();

    if(answerNumber >= 1 && answerNumber <= max) {
      return answerNumber;
    } else {
      printText(message);
      return readIntByInterval(max, message);
    }
  }

}
