package com.rnt.test_passing.service;

public interface IOService {
  void printText(String text);

  String readText();

  int readIntByInterval(int max, String message);
}
