package org.main.repo;

import java.util.List;
import org.main.entity.Question;
import org.main.exception.TestReadingException;

public interface QuestionRepository {
  List<Question> findQuestionsByName(String name) throws TestReadingException;

}
