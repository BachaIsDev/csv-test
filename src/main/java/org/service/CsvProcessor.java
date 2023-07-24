package org.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.entity.Test;
import org.springframework.stereotype.Component;

public class CsvProcessor {

  public Test processTest(Path filePath) {
    List<String[]> list = new ArrayList<>();
    try (Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
      try (CSVReader csvReader = new CSVReader(reader)) {
        String[] line;
        while ((line = csvReader.readNext()) != null) {
          list.add(line);
        }
      }
    } catch (IOException e) {
      System.out.println("Can not access file");;
    } catch (CsvValidationException e){
      System.out.println("Wrong csv format");
    }
    return toObject(list);
  }

  private Test toObject(List<String[]> readTest) {
    Test test = new Test();
    if(readTest.isEmpty()){
      System.out.println("File is empty");
      return test;
    }
    test.setQuestion(readTest.get(0)[0]);
    readTest.remove(0);
    Map<String, Boolean> asd = new HashMap<>();
    for (String[] strings : readTest) {
      test.getAnswers().put(strings[0], Boolean.parseBoolean(strings[1]));
    }
    return test;
  }

}
