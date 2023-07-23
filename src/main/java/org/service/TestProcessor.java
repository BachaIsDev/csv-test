package org.service;

import java.util.Scanner;
import org.entity.Test;

public class TestProcessor {

  public void processTest(Test test) {
    System.out.println("Choose right option - Question: \n"
        + test.getQuestion() + "\n"
        + "Answers:" + "\n"
        + getAnswers(test));
  }

  public String getAnswers(Test test) {
    String answersAsString = "";
    for (String answer : test.getAnswers().keySet()) {
      answersAsString = answersAsString + answer + "\n";
    }
    return answersAsString;
  }

  public boolean isRight(String answer, Test test) {
    boolean right = false;
    try {
      right = test.getAnswers().get(answer);
    } catch (NullPointerException e) {
      System.out.println("There is no such option");
    }

    return right;
  }

  public void processAnswer(){
    Scanner scanner = new Scanner(System.in);

  }
}
