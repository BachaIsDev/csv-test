package org.service;

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

  public void launchTests() {
    String fileName = "";
    System.out.println("Select a test: ");
    testService.showTestNames(RESOURCE_PATH);
    testService.showTestNames(directoryHandler.getBasePath());

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
}
