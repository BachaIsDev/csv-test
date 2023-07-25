package org.repo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.entity.TestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvProcessor {

  private static final Logger logger =  LoggerFactory.getLogger(CsvProcessor.class);


  public TestEntity processTest(String filePath) {
    List<String[]> list = new ArrayList<>();
    try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
      try (CSVReader csvReader = new CSVReader(reader)) {
        String[] line;
        while ((line = csvReader.readNext()) != null) {
          list.add(line);
        }
      }
    } catch (IOException e) {
      System.out.println("Can not access file");
    } catch (CsvValidationException e){
      System.out.println("Wrong csv format");
    }
    return toObject(list);
  }

  private TestEntity toObject(List<String[]> readTest) {
    TestEntity testEntity = new TestEntity();
    if(readTest.isEmpty()){
      System.out.println("File is empty");
      return testEntity;
    }
    testEntity.setQuestion(readTest.get(0)[0]);
    readTest.remove(0);
    Map<String, Boolean> asd = new HashMap<>();
    for (String[] strings : readTest) {
      testEntity.getAnswer().getAnswers().put(strings[0], Boolean.parseBoolean(strings[1]));
    }
    return testEntity;
  }

}
