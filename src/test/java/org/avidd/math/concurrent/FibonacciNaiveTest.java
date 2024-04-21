package org.avidd.math.concurrent;

import org.avidd.math.Fibonacci;
import org.avidd.math.FibonacciNaive;
import org.junit.Ignore;

@Ignore
public class FibonacciNaiveTest extends AbstractFibonacciTest {
  
  @Override
  public Fibonacci fib() {
    return new FibonacciNaive();
  }
}
