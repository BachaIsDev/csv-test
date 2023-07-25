package org.entity;

import java.util.HashMap;

public class Answer {
  private HashMap<String, Boolean> answers = new HashMap<>();

  public HashMap<String, Boolean> getAnswers() {
    return answers;
  }

  public void setAnswers(HashMap<String, Boolean> answers) {
    this.answers = answers;
  }
}
