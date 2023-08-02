package org.main.repo;

import java.util.List;
import org.main.entity.Question;
import org.main.exception.TestException;

public interface QuestionRepository {
  List<Question> getQuestions(String name) throws TestException;

}
