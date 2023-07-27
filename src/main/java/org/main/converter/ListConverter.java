package org.main.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.AbstractBeanField;
import java.io.IOException;
import java.util.List;

public class ListConverter extends AbstractBeanField {

  @Override
  protected Object convert(String value) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(value, List.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
