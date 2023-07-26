package org.entity;

public class Result {
  private Double rightAnswers;
  private Double totalAnswers;

  public Double getRightAnswers() {
    return rightAnswers;
  }

  public void setRightAnswers(Double rightAnswers) {
    this.rightAnswers = rightAnswers;
  }

  public Double getTotalAnswers() {
    return totalAnswers;
  }

  public void setTotalAnswers(Double totalAnswers) {
    this.totalAnswers = totalAnswers;
  }

  public void getResult(){
    System.out.println(rightAnswers/totalAnswers*100 + "% correctly");
  }
}
