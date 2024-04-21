package org.avidd.math;

import java.math.BigInteger;

public final class FibonacciSwap implements Fibonacci {

  public BigInteger fib(int n) {
    if ( n < 0 ) {
      throw new IllegalArgumentException();
    }
    if ( n <= 1 ) {
      return BigInteger.valueOf(n);
    }
    BigInteger[] fibs = new BigInteger[] { BigInteger.ZERO, BigInteger.ONE };
    int j = 1;
    for ( int i = 2; i <= n; i++ ) {
      fibs[1 - j] = fibs[j].add(fibs[1 - j]);
      j = 1 - j;
    }
    return fibs[j];
  }
}
