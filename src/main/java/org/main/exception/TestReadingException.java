package org.main.exception;

public class TestReadingException extends Throwable {
  public TestReadingException(String message, Throwable cause){
    super(message, cause);
  }

  public TestReadingException(String message){
    super(message);
  }
}
