package org.avidd.math.concurrent;

import org.avidd.math.Fibonacci;
import org.avidd.math.FibonacciRecurrence;
import org.junit.Ignore;

@Ignore
public class FibonacciRecurrenceTest extends AbstractFibonacciTest {
  
  @Override
  public Fibonacci fib() {
    return new FibonacciRecurrence();
  }
}
