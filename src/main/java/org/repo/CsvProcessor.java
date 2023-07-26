package org.repo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.entity.TestEntity;


public class CsvProcessor {

  public List<TestEntity> getTests(String filePath) {
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

  private List<TestEntity> toObject(List<String[]> readTest) {
    List<TestEntity> testList = new ArrayList<>();
    if(readTest.isEmpty()){
      System.out.println("File is empty");
      return testList;
    }

    for (String[] strings : readTest) {
      TestEntity test = new TestEntity();
      test.setQuestion(strings[0]);
      test.getAnswer().getAnswers().put(strings[1], true);
      for(int i = 2; i < strings.length; i++){
        test.getAnswer().getAnswers().put(strings[i], false);
      }
      testList.add(test);
    }
    return testList;
  }



}
