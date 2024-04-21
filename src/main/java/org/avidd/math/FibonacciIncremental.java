package org.avidd.math;

import java.math.BigInteger;

public final class FibonacciIncremental implements Fibonacci {

  public BigInteger fib(int n) {
    if ( n < 0 ) {
      throw new IllegalArgumentException();
    }
    if ( n <= 1 ) {
      return BigInteger.valueOf(n);
    }
    BigInteger f2 = BigInteger.ZERO;
    BigInteger f1 = BigInteger.ONE;
    BigInteger fib = f1;
    for ( int i = 2; i <= n; i++ ) {
      fib = f1.add(f2);
      f2 = f1;
      f1 = fib;
    }
    return fib;
  }
}
