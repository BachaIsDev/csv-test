package org.service;

import java.util.Scanner;
import org.entity.Test;

public class TestProcessor {

  public void processTest(Test test) {
    System.out.println("Choose right option - Question: \n"
        + test.getQuestion() + "\n"
        + "Answers:" + "\n"
        + getAnswers(test));

    processAnswer(test);
  }

  private void processAnswer(Test test){
    boolean isCorrect = false;
    System.out.println("Enter an answer: ");
    Scanner scanner = new Scanner(System.in);

    if(scanner.hasNext()){
      isCorrect = isRight(scanner.nextLine(), test);
    }

    if (isCorrect) {
      System.out.println("You're right!");
    } else {
      System.out.println("That's not right");
    }

  }

  private boolean isRight(String answer, Test test) {
    boolean right = false;
    try {
      right = test.getAnswers().get(answer);
    } catch (NullPointerException e) {
      System.out.println("There is no such option!");
    }

    return right;
  }

  private String getAnswers(Test test) {
    String answersAsString = "";
    for (String answer : test.getAnswers().keySet()) {
      answersAsString = answersAsString + answer + "\n";
    }

    return answersAsString;
  }
}
