package com.rnt.test_passing.exception;

public class SourceConnectException extends RuntimeException {

  public SourceConnectException(String message) {
    super(message);
  }

  public SourceConnectException(String message, Throwable cause) {
    super(message, cause);
  }
}
