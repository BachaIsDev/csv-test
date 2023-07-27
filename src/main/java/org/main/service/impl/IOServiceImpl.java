package org.main.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.main.entity.Question;
import org.main.entity.Result;
import org.main.service.IOService;
import org.main.util.PathProvider;

public class IOServiceImpl implements IOService {

  private final PathProvider pathProvider;

  public IOServiceImpl(PathProvider pathProvider) {
    this.pathProvider = pathProvider;
  }

  @Override
  public String askFileName(){
    System.out.println("Select a test: ");
    showTestNames(getClass().getClassLoader().getResource("tests").getPath());
    showTestNames(pathProvider.getBasePath());

    String fileName = "";
    Scanner scanner = new Scanner(System.in);

    if (scanner.hasNext()) {
      fileName = scanner.nextLine();
    }

    return fileName;
  }

  @Override
  public boolean askQuestion(Question question){
    Boolean isCorrect;
    String answersAsString = getAnswersAsString(question);
    System.out.println(question.getIssue() + "\n"
        + "Answers:" + "\n"
        + answersAsString);
    System.out.println("Enter an answer: ");

    Scanner scanner = new Scanner(System.in);
    do{
      isCorrect = false;
      if(scanner.hasNext()){
        isCorrect = question.getOption().getOptions().getOrDefault(scanner.nextLine(), null);
        if(isCorrect == null){
          System.out.println("There is no such option. Please, try again");
        }
      }
    }while (isCorrect == null);

    return isCorrect;
  }

  @Override
  public void greetings(){
    System.out.println("Answer correctly as many questions as possible. Good Luck!");
  }

  @Override
  public void getResult(Result result) {
    System.out.println(((double) result.getRightAnswers() / (double) result.getTotalAnswers()) * 100
        + "% correctly");
  }

  private void showTestNames(String path){
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
      return fileNames;
    }
    for (File file : listOfFiles) {
      if (file.isFile()) {
        fileNames.add(file.getName());
      }
    }

    return fileNames;
  }

  private String getAnswersAsString(Question question) {
    String answersAsString = "";
    for (String answer : question.getOption().getOptions().keySet()) {
      answersAsString = answersAsString + answer + "\n";
    }

    return answersAsString;
  }

}
