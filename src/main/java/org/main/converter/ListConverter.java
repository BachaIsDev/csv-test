package org.main.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.opencsv.bean.AbstractBeanField;
import java.io.IOException;
import java.util.List;

public class ListConverter extends AbstractBeanField {

  @Override
  protected Object convert(String value) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(value, List.class);
      // К сожалению не могу здесь использовать свой собственный IO сервис
    } catch (MismatchedInputException e) {
      System.out.println("Wrong format in the file");
    } catch (IOException e) {
      System.out.println("Error occurred");
    }
    return null;
  }
}
