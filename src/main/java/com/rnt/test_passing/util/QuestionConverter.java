package com.rnt.test_passing.util;

import com.rnt.test_passing.entity.Question;

public interface QuestionConverter {
  String getAnswersAsString(Question question);
}
