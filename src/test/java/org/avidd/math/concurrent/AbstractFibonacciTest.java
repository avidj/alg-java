package org.avidd.math.concurrent;

import org.avidd.math.Fibonacci;
import org.junit.jupiter.api.Test;

public abstract class AbstractFibonacciTest {
  
  public abstract Fibonacci fib();

  @Test
  public void testFib10() {
    int n = 10;
    System.err.println("Fib(" + n + ") = " + fib().fib(n));
  }

  @Test
  public void testFib20() {
    int n = 20;
    System.err.println("Fib(" + n + ") = " + fib().fib(n));
  }

  @Test
  public void testFib30() {
    int n = 30;
    System.err.println("Fib(" + n + ") = " + fib().fib(n));
  }

  @Test
  public void testFib40() {
    int n = 40;
    System.err.println("Fib(" + n + ") = " + fib().fib(n));
  }

  @Test
  public void testFib45() {
    int n = 45;
    System.err.println("Fib(" + n + ") = " + fib().fib(n));
  }
}
