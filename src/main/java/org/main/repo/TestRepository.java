package org.main.repo;

import java.util.List;
import org.main.entity.Question;
import org.main.exception.TestException;

public interface TestRepository {
  List<Question> getQuestions(String filePath) throws TestException;
}
