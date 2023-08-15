package com.rnt.test_passing.service;

import java.util.List;
import com.rnt.test_passing.entity.Question;

public interface TestExecutor {
  void startTest(List<Question> testList);
}
