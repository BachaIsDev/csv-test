package org.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.entity.Result;
import org.entity.TestEntity;
import org.repo.CsvProcessor;
import org.util.DirectoryHandler;

public class AppService {
  private final CsvProcessor csvProcessor;
  private final TestService testService;
  private final DirectoryHandler directoryHandler;
  private final String RESOURCE_PATH = "src/main/resources/tests/";

  public AppService(CsvProcessor csvProcessor, TestService testService,
      DirectoryHandler directoryHandler) {
    this.csvProcessor = csvProcessor;
    this.testService = testService;
    this.directoryHandler = directoryHandler;
  }

  public void launch() {
    String fileName = "";
    System.out.println("Select a test: ");
    loadTestNames(RESOURCE_PATH);
    loadTestNames(directoryHandler.getBasePath());

    Scanner scanner = new Scanner(System.in);

    if (scanner.hasNext()) {
      fileName = scanner.nextLine();
    }
    System.out.println("Answer correctly as many questions as possible. Good Luck!");
    if(directoryHandler.checkFile(RESOURCE_PATH + fileName)){
      launchTest(RESOURCE_PATH + fileName);
    }else {
      launchTest(directoryHandler.getBasePath() + fileName);
    }
  }

  private void launchTest(String path){
    List<TestEntity> testList = csvProcessor.getTests(path);
    if(testList.isEmpty()){
      System.out.println("Test is unavailable");
      return;
    }
    Result result = testService.processTests(testList);

    result.getResult();
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

  private void loadTestNames(String path){
    List<String> fileNames = getTestNames(path);
    String namesAsString = "";
    for (String name : fileNames) {
      namesAsString = namesAsString + name + "\n";
    }
    System.out.print(namesAsString);
  }

}
