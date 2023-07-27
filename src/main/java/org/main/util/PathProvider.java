package org.main.util;

import java.io.File;

public class PathProvider {
  private final String basePath;

  public PathProvider(String basePath){
    this.basePath = basePath;
  }

  public String getBasePath() {
    return basePath;
  }

  public boolean checkFile(String path){
    File file = new File(path);

    return file.exists();
  }

  public String getPath(String fileName){
    String pathToFile = "";
    ClassLoader classLoader = getClass().getClassLoader();
    String resourcePath = classLoader.getResource("tests").getPath()
        + '\\';
    resourcePath = resourcePath.substring(1).replace("/", "\\");
    pathToFile = checkFile(resourcePath + fileName) ? (resourcePath + fileName)
        : (getBasePath() + fileName);

    return pathToFile;
  }
}
