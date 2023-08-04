package com.rnt.test_passing.util;

import com.rnt.test_passing.entity.Question;

public interface QuestionConverter {
  String getAnswerAsString(Question question);
}
