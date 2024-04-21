package org.avidd.math;

import java.math.BigInteger;

public final class FibonacciAll implements Fibonacci {

  public BigInteger fib(int n) {
    if ( n < 0 ) {
      throw new IllegalArgumentException();
    }
    if ( n <= 1 ) {
      return BigInteger.valueOf(n);
    }
    BigInteger[] fibs = new BigInteger[n + 1];
    fibs[0] = BigInteger.ZERO;
    fibs[1] = BigInteger.ONE;
    for ( int i = 2; i <= n; i++ ) {
      fibs[i] = fibs[i - 1].add(fibs[i - 2]);
    }
    return fibs[n];
  }
}
