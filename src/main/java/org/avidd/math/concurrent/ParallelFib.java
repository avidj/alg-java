package org.avidd.math.concurrent;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

import org.avidd.math.Fibonacci;

public final class ParallelFib implements Fibonacci {
  private static final ForkJoinPool POOL = new ForkJoinPool();
  private final Fibonacci fib;
  
  public ParallelFib(Fibonacci fib) {
    this.fib = fib;
  }

  public BigInteger fib(int n) {
    BigInteger result = POOL.invoke(new FibTask(fib, n));
    return result;
  }
}
