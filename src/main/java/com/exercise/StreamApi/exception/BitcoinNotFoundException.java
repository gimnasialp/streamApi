package com.exercise.StreamApi.exception;

public class BitcoinNotFoundException extends RuntimeException {

  public BitcoinNotFoundException() {
    super("Could not find Bitcoin by input times");
  }

}
