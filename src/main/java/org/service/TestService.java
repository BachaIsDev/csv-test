package org.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.entity.Result;
import org.entity.TestEntity;

public class TestService {
  public Result processTests(List<TestEntity> testList){
    Result result = new Result();
    result.setTotalAnswers((double)testList.size());
    result.setRightAnswers(0.0);
    for(TestEntity test: testList){
      Boolean rightAnswer = processTest(test);
      if(rightAnswer){
        result.setRightAnswers(result.getRightAnswers() + 1);
      }
    }

    return result;
  }

  private Boolean processTest(TestEntity testEntity) {
    System.out.println(testEntity.getQuestion() + "\n"
        + "Answers:" + "\n"
        + getAnswersAsString(testEntity));

    return processAnswer(testEntity);
  }

  private Boolean processAnswer(TestEntity testEntity){
    Boolean isCorrect;
    System.out.println("Enter an answer: ");
    Scanner scanner = new Scanner(System.in);

    do{
      isCorrect = false;
      if(scanner.hasNext()){
        isCorrect = testEntity.getAnswer().getAnswers().getOrDefault(scanner.nextLine(), null);
        if(isCorrect == null){
          System.out.println("There is no such option. Please, try again");
        }
      }
    }while (isCorrect == null);

    return isCorrect;
  }

  private String getAnswersAsString(TestEntity testEntity) {
    String answersAsString = "";
    for (String answer : testEntity.getAnswer().getAnswers().keySet()) {
      answersAsString = answersAsString + answer + "\n";
    }

    return answersAsString;
  }

  public void showTestNames(String path){
    List<String> fileNames = getTestNames(path);
    String namesAsString = "";
    for (String name : fileNames) {
      namesAsString = namesAsString + name + "\n";
    }
    System.out.print(namesAsString);
  }

  private List<String> getTestNames(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    List<String> fileNames = new ArrayList<>();
    if (listOfFiles == null) {
    }
    for (File file : listOfFiles) {
      if (file.isFile()) {
        fileNames.add(file.getName());
      }
    }

    return fileNames;
  }
}
