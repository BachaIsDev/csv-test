package org.main.service;

import java.util.List;
import org.main.entity.Question;
import org.main.entity.Result;

public interface TestService {
  Result startTest(List<Question> testList);

  String getTestPath(String fileName);
}
