package org.main.entity;

public class Result {
  private int rightAnswers;
  private int totalAnswers;

  public Result(int rightAnswers, int totalAnswers) {
    this.rightAnswers = rightAnswers;
    this.totalAnswers = totalAnswers;
  }

  public int getRightAnswers() {
    return rightAnswers;
  }

  public void setRightAnswers(int rightAnswers) {
    this.rightAnswers = rightAnswers;
  }

  public int getTotalAnswers() {
    return totalAnswers;
  }

  public void setTotalAnswers(int totalAnswers) {
    this.totalAnswers = totalAnswers;
  }

}
