package org.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryHandler {
  private String basePath;

  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public boolean checkFile(String path){
    File file = new File(path);

    if(file.exists()){
      return true;
    }

    return false;
  }
}
