package org.avidd.math.concurrent;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

import org.avidd.math.Fibonacci;

public class FibTask extends RecursiveTask<BigInteger> {
  private static final long serialVersionUID = 1L;
  private static final int sequentialThreshold = 10;
  private volatile int number;

  private transient final Fibonacci fib;
  
  FibTask(Fibonacci fib, int n) {
    this.fib = fib;
    number = n;
  }

  @Override
  protected BigInteger compute() {
    int n = number;
    if ( n <= sequentialThreshold ) {
      return fib.fib(n);
    } else {
      FibTask f1 = new FibTask(fib, n - 1);
      FibTask f2 = new FibTask(fib, n - 2);
      f1.fork();
      // f2.compute() will recursively spawn tasks.
      return f2.compute().add(f1.join());
      //
      // FibTask f1 = new FibTask(n - 1);
      // FibTask f2 = new FibTask(n - 2);
      // invokeAll(f1, f2);
      // number = f1.number + f2.number;
    }
  }
}
