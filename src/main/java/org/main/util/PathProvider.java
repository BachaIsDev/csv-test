package org.main.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.main.exception.TestReadingException;

public class PathProvider {

  private List<String> basePaths;
  private final String RESOURCE_PATH = "tests";
  public PathProvider(List<String> basePaths){
    List<String> newPaths = new ArrayList<>();
    basePaths.add("tests\\");
    for(String path: basePaths){
      String newPath = path.replace("/", "\\");
      newPaths.add(newPath);
    }
    this.basePaths = newPaths;
  }

  public void setBasePaths(List<String> basePaths) {
    this.basePaths = basePaths;
  }

  public boolean checkFile(String path){
    File file = new File(path);

    return file.exists();
  }

  public String getPath(String fileName){
    ClassLoader classLoader = getClass().getClassLoader();

    String resource = classLoader.getResource(RESOURCE_PATH).getPath() + '\\';
    if(checkFile(resource + fileName)){
      return "tests\\" + fileName;
    }
    String pathToFile = "";
    for(String basePath: basePaths){
      if(checkFile(basePath + fileName)){
        pathToFile = basePath + fileName;
        break;
      }
    }

    return pathToFile;
  }

  public List<String> getAllTestNames() throws TestReadingException {
    List<String> fileNames = new ArrayList<>();
    for(String basePath: basePaths){
      fileNames.addAll(getTestNamesFromDir(basePath));
    }

    return fileNames;
  }

  private List<String> getTestNamesFromDir(String path) throws TestReadingException {
    if(path.startsWith(RESOURCE_PATH)){
      ClassLoader classLoader = getClass().getClassLoader();

      URL resource = classLoader.getResource(RESOURCE_PATH);

      List<String> fileNames = null;
      try {
        fileNames = Files.walk(Paths.get(resource.toURI()))
            .filter(Files::isRegularFile)
            .map(x -> x.getFileName().toString())
            .collect(Collectors.toList());
      } catch (IOException | URISyntaxException e) {
        throw new TestReadingException("There is no such test", e);
      }

      return fileNames;
    }

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

}
