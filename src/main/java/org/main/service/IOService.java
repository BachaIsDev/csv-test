package org.main.service;

import org.main.entity.Question;
import org.main.entity.Result;

public interface IOService {
  boolean askQuestion(Question test);

  void getResult(Result result);

  String askFileName();

  void greetings();
}
