package org.avidd.math;

import java.math.BigInteger;

public final class FibonacciDynamic implements Fibonacci {
  private final BigInteger[] cache = new BigInteger[1000];

  public FibonacciDynamic() {
    cache[0] = BigInteger.ZERO;
    cache[1] = BigInteger.ONE;
  }
  
  @Override
  public BigInteger fib(int n) {
    if ( n < 0 || n > cache.length ) {
      throw new IllegalArgumentException();
    }
    synchronized ( cache ) {
      if ( cache[n] != null ) {
        return cache[n];
      }
    }
    BigInteger fib = fib(n - 1).add(fib(n - 2));
    synchronized ( cache ) {
      cache[n] = fib;
    }
    return fib;
  }
}
