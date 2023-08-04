package com.rnt.test_passing.util.impl;

import com.rnt.test_passing.entity.Option;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.util.QuestionConverter;

public class QuestionConverterImpl implements QuestionConverter {

  @Override
  public String getAnswerAsString(Question question) {
    String answersAsString = "";
    List<String> optionsAsStrings = question.getOptions().stream().map(Option::getText).toList();
    for (String answer : optionsAsStrings) {
      answersAsString = answersAsString + answer + "\n";
    }

    return answersAsString;
  }
}
