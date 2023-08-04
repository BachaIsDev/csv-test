package com.rnt.test_passing.entity;

import java.util.List;

public class Question {

  private String issue;

  private List<Option> options;

  public Question() {
  }

  public Question(String issue, List<Option> options) {
    this.issue = issue;
    this.options = options;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }
}
