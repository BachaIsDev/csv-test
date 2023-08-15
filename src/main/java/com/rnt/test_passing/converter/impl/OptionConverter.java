package com.rnt.test_passing.converter.impl;

import com.rnt.test_passing.entity.Option;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.core.convert.converter.Converter;

public class OptionConverter implements Converter<List<Option>, String> {
  @Override
  public String convert(List<Option> options) {
    return IntStream.range(1, options.size() + 1)
        .mapToObj(i -> i + "." + options.get(i - 1).getText())
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
