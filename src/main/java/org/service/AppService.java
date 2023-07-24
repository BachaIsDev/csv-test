package org.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.entity.Test;

public class AppService {

  private final CsvProcessor csvProcessor;
  private final TestProcessor testProcessor;
  private final DirectoryHandler directoryHandler;

  AppService(CsvProcessor csvProcessor, TestProcessor testProcessor,
      DirectoryHandler directoryHandler) {
    this.csvProcessor = csvProcessor;
    this.testProcessor = testProcessor;
    this.directoryHandler = directoryHandler;
  }

  public void launch() {
    System.out.println("1. Load test  \n"
        + "2. Launch existing tests \n"
        + "Press Enter to exit");

    Scanner scanner = new Scanner(System.in);
    int choiceAsInt = 0;
    if (scanner.hasNext()) {
      String choice = scanner.next();
      try {
        choiceAsInt = Integer.parseInt(choice);
      } catch (NumberFormatException e) {
        System.out.println("incorrect input!");
      }
    }

    if (choiceAsInt == 1) {
      launchWithExternalTest();
    } else if (choiceAsInt == 2) {
      launchWithInternalTest();
    }
  }

  private void launchWithExternalTest() {
    String pathAsString = directoryHandler.getBasePath();
    launchTest(pathAsString);
  }

  private void launchWithInternalTest() {
    String pathAsString = "src/main/resources/tests/";
    launchTest(pathAsString);
  }

  private void launchTest(String pathAsString){
    loadTestMessage(pathAsString);

    Scanner scanner = new Scanner(System.in);

    if (scanner.hasNext()) {
      Path path = Paths.get(pathAsString + scanner.next());

      Test test = csvProcessor.processTest(path);
      if((test == null) || (test.getQuestion() == null)){
        System.out.println("Test is unavailable");
        return;
      }
      testProcessor.processTest(test);
    }
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

  private void loadTestMessage(String path){
    List<String> fileNames = getTestNames(path);
    String namesAsString = "";
    for (String name : fileNames) {
      namesAsString = namesAsString + name + "\n";
    }
    System.out.println("Print test's name  \n"
        + "Available tests: \n"
        + namesAsString + "\n"
        + "Enter name of test: ");
  }

}
