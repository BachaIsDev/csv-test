package org.main.entity;

public class Question {

  private String issue;
  private Option option = new Option();

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }

  public Option getOption() {
    return option;
  }
}
