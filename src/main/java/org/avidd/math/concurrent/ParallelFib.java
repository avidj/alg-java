package org.avidd.math.concurrent;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

import org.avidd.math.Fibonacci;

/**
 * Parallel computation of fib. The question is how to parallelize? The approach
 * is using a thread-safe variant of the naive computation with memoization: 
 * the FibonacciDynamic implementation.
 */
public final class ParallelFib implements Fibonacci, AutoCloseable {
  private final ForkJoinPool pool = new ForkJoinPool();
  private final Fibonacci fib;
  
  public ParallelFib(Fibonacci fib) {
    this.fib = fib;
  }

  @Override
  public BigInteger fib(int n) {
    BigInteger result = pool.invoke(new FibTask(fib, n));
    return result;
  }
  
  @Override
  public void close() {
    pool.shutdown();
  }
}
