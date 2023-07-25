package org.service;

import java.util.Scanner;
import org.entity.TestEntity;

public class TestService {

  public void processTest(TestEntity testEntity) {
    System.out.println("Choose right option - Question: \n"
        + testEntity.getQuestion() + "\n"
        + "Answers:" + "\n"
        + getAnswers(testEntity));

    processAnswer(testEntity);
  }

  private void processAnswer(TestEntity testEntity){
    boolean isCorrect = false;
    System.out.println("Enter an answer: ");
    Scanner scanner = new Scanner(System.in);

    if(scanner.hasNext()){
      isCorrect = isRight(scanner.nextLine(), testEntity);
    }

    if (isCorrect) {
      System.out.println("You're right!");
    } else {
      System.out.println("That's not right");
    }

  }

  private boolean isRight(String answer, TestEntity testEntity) {
    boolean right = false;
    try {
      right = testEntity.getAnswer().getAnswers().get(answer);
    } catch (NullPointerException e) {
      System.out.println("There is no such option!");
    }

    return right;
  }

  private String getAnswers(TestEntity testEntity) {
    String answersAsString = "";
    for (String answer : testEntity.getAnswer().getAnswers().keySet()) {
      answersAsString = answersAsString + answer + "\n";
    }

    return answersAsString;
  }
}
