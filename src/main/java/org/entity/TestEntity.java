package org.entity;

public class TestEntity {
  private String question;
  private Answer answer = new Answer();

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Answer getAnswer() {
    return answer;
  }
  public void setAnswer(Answer answer) {
    this.answer = answer;
  }
}
