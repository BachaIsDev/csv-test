package com.rnt.test_passing.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import com.rnt.test_passing.service.IOService;

public class IOServiceImpl implements IOService {

  private final PrintStream printStream = new PrintStream(System.out);
  private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  @Override
  public void printText(String text) {
    printStream.println(text);
  }

  @Override
  public String readText() {
    String text = null;
    try {
      text = reader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return text;
  }

}
