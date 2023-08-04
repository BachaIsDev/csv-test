package com.rnt.test_passing.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.opencsv.bean.AbstractBeanField;
import java.io.IOException;
import java.util.List;
import com.rnt.test_passing.exception.TestReadingException;

public class ListConverter extends AbstractBeanField {

  @Override
  protected Object convert(String value) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(value, List.class);

    } catch (MismatchedInputException e) {
      throw new TestReadingException("Wrong format in the file");

    } catch (IOException e) {
      throw new TestReadingException("Error occurred");
    }
  }
}
