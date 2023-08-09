package com.rnt.test_passing.converter.impl;

import com.rnt.test_passing.converter.ConversationService;
import com.rnt.test_passing.entity.Question;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionConverter implements ConversationService<Question, String> {
  @Override
  public String convert(Question question) {
    return IntStream.range(1, question.getOptions().size() + 1)
        .mapToObj(i -> i + "." + question.getOptions().get(i - 1).getText())
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
