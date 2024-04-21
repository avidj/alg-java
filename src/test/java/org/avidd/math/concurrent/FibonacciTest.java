package org.avidd.math.concurrent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.avidd.math.FibonacciAll;
import org.avidd.math.FibonacciIncremental;
import org.avidd.math.FibonacciRecurrence;
import org.avidd.math.FibonacciSwap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class FibonacciTest {

  @Test
  @Ignore
  public void testAllFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000, 100000 };
    for ( int n : values ) {
      System.err.println("fibAll(" + n + ") = " + new FibonacciAll().fib(n));
    }
  }

  @Test
  public void testIncrementalFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000, 100000 };
    for ( int n : values ) {
      System.err.println("fibIncremental(" + n + ") = " + new FibonacciIncremental().fib(n));
    }
  }

  @Test
  public void testSwapFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000, 100000 };
    for ( int n : values ) {
      System.err.println("fibSwap(" + n + ") = " + new FibonacciSwap().fib(n));
    }
  }

  @Test
  @Ignore
  public void testRecurrenceFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000, 100000,
        1000000 };
    for ( int n : values ) {
      System.err.println("fibRecurrence(" + n + ") = " + new FibonacciRecurrence().fib(n));
    }
  }

  @Test
  public void testAllEqual() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000 };
    for ( int n : values ) {
      System.err.println("fibAll(" + n + ") = " + new FibonacciAll().fib(n));
      System.err.println("fibIncremental(" + n + ") = " + new FibonacciIncremental().fib(n));
      System.err.println("fibSwap(" + n + ") = " + new FibonacciSwap().fib(n));
      Assert.assertThat(new FibonacciAll().fib(n), is(equalTo(new FibonacciIncremental().fib(n))));
      Assert.assertThat(new FibonacciAll().fib(n), is(equalTo(new FibonacciSwap().fib(n))));
    }
  }
}
