package com.rnt.test_passing.repo;

import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.exception.TestReadingException;

public interface QuestionRepository {
  List<Question> findQuestionsByTestName(String name) throws TestReadingException;

  List<String> getQuestionTopicNames() throws TestReadingException;

}
