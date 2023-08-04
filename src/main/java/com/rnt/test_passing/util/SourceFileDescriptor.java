package com.rnt.test_passing.util;

public class SourceFileDescriptor {
  private String fileName;
  boolean fromResources;

  public SourceFileDescriptor(String fileName, boolean fromResources) {
    this.fileName = fileName;
    this.fromResources = fromResources;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public boolean isFromResources() {
    return fromResources;
  }

  public void setFromResources(boolean fromResources) {
    this.fromResources = fromResources;
  }
}
