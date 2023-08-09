package com.rnt.test_passing.converter;

import org.springframework.core.convert.converter.Converter;

public interface ConversationService<R, T> extends Converter<R, T> {
  T convert(R t);
}
