package org.main.exception;

public class TestException extends Throwable {
  public TestException(String message, Throwable cause){
    super(message, cause);
  }

  public TestException(String message){
    super(message);
  }
}
