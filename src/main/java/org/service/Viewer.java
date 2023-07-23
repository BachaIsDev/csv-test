package org.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.entity.Test;

public class Viewer {

  private final CsvProcessor csvProcessor;
  private final TestProcessor testProcessor;

  Viewer(CsvProcessor csvProcessor, TestProcessor testProcessor) {
    this.csvProcessor = csvProcessor;
    this.testProcessor = testProcessor;
  }

  public void showOnConsole(Test test) {
    System.out.println(
        "Read question and enter the number of the answer: " + "\n"
            + test.getQuestion() + "\n"
            + testProcessor.getAnswers(test)
    );
  }

  private void launchWithExternal() {
    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      Path path = Paths.get(scanner.next());

      Test test = csvProcessor.processTest(path);
      testProcessor.processTest(test);
    }
  }

  private void launchWithInternal() {
    List<String> fileNames = getInternalTests();
    String namesAsString = "";
    for (String name : fileNames) {
      namesAsString = namesAsString + name + "\n";
    }
    System.out.println("Print internal test's name  \n"
        + "Available tests: \n"
        + namesAsString + "\n"
        + "Enter name of test: ");

    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      Path path = Paths.get("src/main/resources/tests/" + scanner.next());

      Test test = csvProcessor.processTest(path);
      testProcessor.processTest(test);
    }
  }

  private List<String> getInternalTests() {
    File folder = new File("src/main/resources/tests");
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

  public void launch() {
    System.out.println("1. Load test  \n"
        + "2. Launch existing tests");

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
    switch (choiceAsInt) {
      case 1:
        launchWithExternal();
      case 2:
        launchWithInternal();
    }
  }
}
