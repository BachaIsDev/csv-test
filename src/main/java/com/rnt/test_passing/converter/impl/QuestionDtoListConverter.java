package com.rnt.test_passing.converter.impl;

import com.rnt.test_passing.entity.Option;
import com.rnt.test_passing.entity.Question;
import com.rnt.test_passing.entity.dto.QuestionDTO;
import com.rnt.test_passing.exception.TestReadingException;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QuestionDtoListConverter implements Converter<List<QuestionDTO>, List<Question>> {
  @Override
  public List<Question> convert(List<QuestionDTO> source) {
    try{
      return source.stream()
          .map(dto -> {
            List<Option> resultOption = dto.getOptions().stream()
                .map(this::stringToOption).toList();
            return new Question(dto.getIssue(), resultOption);
          }).toList();
    } catch (IndexOutOfBoundsException e){
      throw new TestReadingException("Can't convert DTO to Object", e);
    }
  }
  private Option stringToOption(String option) {
    boolean isRight = option.contains("right");
    String[] parts = option.split("-");
    return new Option(parts[0], isRight);
  }
}
