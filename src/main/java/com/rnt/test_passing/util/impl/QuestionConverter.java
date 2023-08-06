package com.rnt.test_passing.util.impl;

import com.rnt.test_passing.entity.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rnt.test_passing.entity.Question;
import org.springframework.core.convert.converter.Converter;


public class QuestionConverter implements Converter<Question, List<String>> {
  @Override
  public List<String> convert(Question question) {
    List<String> optionsAsStrings = question.getOptions().stream().map(Option::getText).toList();
    List<String> shuffledOptions = new ArrayList<>(optionsAsStrings);
    shuffledOptions.add(shuffledOptions.get(0) + ",true");
    shuffledOptions.remove(0);
    Collections.shuffle(shuffledOptions);
    List<String> result = new ArrayList<>();
    for(int i = 0; i < shuffledOptions.size(); i++){
      result.add(i + 1 + "." + shuffledOptions.get(i));
    }
    return result;
  }

}
