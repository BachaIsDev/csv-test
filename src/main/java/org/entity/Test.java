package org.entity;

import java.util.HashMap;
import java.util.Map;

public class Test {
  private String name;
  private String question;
  private Map<String, Boolean> answers = new HashMap<>();

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Map<String, Boolean> getAnswers() {
    return answers;
  }

}
