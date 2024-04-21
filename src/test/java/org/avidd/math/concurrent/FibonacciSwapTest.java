package org.avidd.math.concurrent;

import org.avidd.math.Fibonacci;
import org.avidd.math.FibonacciSwap;

public class FibonacciSwapTest extends AbstractFibonacciTest {
  
  @Override
  public Fibonacci fib() {
    return new FibonacciSwap();
  }
}
