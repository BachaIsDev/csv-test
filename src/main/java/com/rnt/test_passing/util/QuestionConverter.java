package com.rnt.test_passing.util;

import com.rnt.test_passing.entity.Option;
import java.util.ArrayList;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import org.springframework.core.convert.converter.Converter;


public class QuestionConverter implements Converter<Question, String> {
  @Override
  public String convert(Question question) {
    StringBuilder stringBuilder = new StringBuilder();
    List<String> immutableString = question.getOptions().stream().map(Option::getText).toList();
    List<String> optionsAsList = new ArrayList<>(immutableString);

    for(int i = 0; i < optionsAsList.size(); i++){
      optionsAsList.set(i, i + 1 + "." + optionsAsList.get(i));
    }

    optionsAsList.forEach(option ->
        stringBuilder.append(option)
        .append("\n"));

    return stringBuilder.toString();
  }

}
