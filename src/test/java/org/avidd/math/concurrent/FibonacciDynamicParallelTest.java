package org.avidd.math.concurrent;

import org.avidd.math.Fibonacci;
import org.avidd.math.FibonacciDynamic;

public class FibonacciDynamicParallelTest extends AbstractFibonacciTest {
  
  @Override
  public Fibonacci fib() {
    return new ParallelFib(new FibonacciDynamic());
  }
}
