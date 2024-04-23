package org.avidd.math;

import java.math.BigInteger;

public final class FibonacciNaive implements Fibonacci {

  @Override
  public BigInteger fib(int n) {
    if ( n < 0 ) {
      throw new IllegalArgumentException();
    }
    if ( n <= 1 ) {
      return BigInteger.valueOf(n);
    }
    return fib(n - 1).add(fib(n - 2));
  }
}
