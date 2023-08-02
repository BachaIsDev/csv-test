package org.main.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathProvider {

  private List<String> basePaths;

  public PathProvider(List<String> basePaths){
    List<String> newPaths = new ArrayList<>();
    String resourcePath = getClass().getClassLoader().getResource("tests").getPath() + '\\';
    resourcePath = resourcePath.substring(1).replace("/","\\");
    basePaths.add(resourcePath);
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
    String pathToFile = "";
    for(String basePath: basePaths){
      if(checkFile(basePath + fileName)){
        pathToFile = basePath + fileName;
        break;
      }
    }

    return pathToFile;
  }

  public List<String> getAllTestNames(){
    List<String> fileNames = new ArrayList<>();
    for(String basePath: basePaths){
      fileNames.addAll(getTestNamesFromDir(basePath));
    }

    return fileNames;
  }

  private List<String> getTestNamesFromDir(String path) {
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
