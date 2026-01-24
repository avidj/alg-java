package org.avidd.math;

import java.math.BigInteger;

/**
 * This is basically again the incremental variant, but instead of sliding the
 * intermediate values through fib-1 and fib-2, this alternates between accessing
 * the same values from a 2-dim array. An experiment.
 */
public final class FibonacciSwap implements Fibonacci {

  @Override
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
