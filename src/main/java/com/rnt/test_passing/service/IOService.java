package com.rnt.test_passing.service;

import com.rnt.test_passing.entity.Question;

public interface IOService {

  void printText(String text);

  String readText();

  int readIntByInterval(int max);
}
