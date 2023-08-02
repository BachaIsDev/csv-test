package org.main.entity;

public class Question {

  public Question() {
  }
  public Question(String issue, OptionCatalogue optionCatalogue) {
    this.issue = issue;
    this.optionCatalogue = optionCatalogue;
  }
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
