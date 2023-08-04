package com.rnt.test_passing.exception;

public class TestReadingException extends RuntimeException {
  public TestReadingException(String message, Throwable cause){
    super(message, cause);
  }

  public TestReadingException(String message){
    super(message);
  }
}
