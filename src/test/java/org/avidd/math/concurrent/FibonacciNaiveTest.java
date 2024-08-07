package org.avidd.math.concurrent;

import org.avidd.math.Fibonacci;
import org.avidd.math.FibonacciNaive;
import org.junit.jupiter.api.Disabled;

@Disabled
public class FibonacciNaiveTest extends AbstractFibonacciTest {
  
  @Override
  public Fibonacci fib() {
    return new FibonacciNaive();
  }
}
