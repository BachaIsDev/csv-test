package com.rnt.test_passing.entity;

public class Option {

  public Option() {
  }

  public Option(String text, boolean correct) {
    this.text = text;
    this.correct = correct;
  }

  private String text;

  private boolean correct;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isCorrect() {
    return correct;
  }

  public void setCorrect(boolean correct) {
    this.correct = correct;
  }
}
