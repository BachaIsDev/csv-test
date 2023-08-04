package com.rnt.test_passing.service;

import java.util.List;
import com.rnt.test_passing.entity.Question;

public interface QuestionService {

  List<Question> getQuestions(String testName);

}
