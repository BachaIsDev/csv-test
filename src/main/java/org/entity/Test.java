package org.entity;

import java.util.Map;

public class Test {
  private String question;

  private Map<String, Boolean> answerOptions;

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Map<String, Boolean> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(Map<String, Boolean> answerOptions) {
    this.answerOptions = answerOptions;
  }
}
