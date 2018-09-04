package com.juanroldanbrz.examples.banktransfer.exception;

public class ServiceException extends RuntimeException {

  public ServiceException(String msg) {
    super(msg);
  }

  public ServiceException(String msg, Throwable throwable) {
    super(msg, throwable);
  }
}
