package org.main.entity;

public class Question {

  private String issue;
  private OptionCatalogue optionCatalogue = new OptionCatalogue();

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }

  public OptionCatalogue getOption() {
    return optionCatalogue;
  }
}
