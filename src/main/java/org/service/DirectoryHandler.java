package org.service;

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

  public Path connectToCatalog(){
    return Paths.get(basePath);
  }
}
