package org.avidd.math.concurrent;

import java.math.BigInteger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.avidd.math.FibonacciAll;
import org.avidd.math.FibonacciDynamic;
import org.avidd.math.FibonacciIncremental;
import org.avidd.math.FibonacciRecurrence;
import org.avidd.math.FibonacciSwap;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class FibonacciTest {

  @Test
  public void testAllFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000 };
    for ( int n : values ) {
      System.err.println("fibAll(" + n + ") = " + new FibonacciAll().fib(n));
    }
  }

  @Test
  public void testIncrementalFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000 };
    for ( int n : values ) {
      System.err.println("fibIncremental(" + n + ") = " + new FibonacciIncremental().fib(n));
    }
  }

  @Test
  public void testSwapFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000 };
    for ( int n : values ) {
      System.err.println("fibSwap(" + n + ") = " + new FibonacciSwap().fib(n));
    }
  }

  @Test
  public void testRecurrenceFib() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000 };
    for ( int n : values ) {
      System.err.println("fibRecurrence(" + n + ") = " + new FibonacciRecurrence().fib(n));
    }
  }

  @Test
  public void testAllEqualSlow() {
    try ( ParallelFib parallelFib = new ParallelFib(new FibonacciDynamic()) ) { 
      int[] values = { 10, 20, 30, 40 };
      for ( int n : values ) {
        BigInteger all = new FibonacciAll().fib(n);
        BigInteger inc = new FibonacciIncremental().fib(n);
        BigInteger swp = new FibonacciSwap().fib(n);
        BigInteger dyn = new FibonacciDynamic().fib(n);
        BigInteger rec = new FibonacciRecurrence().fib(n);
        BigInteger parDyn = parallelFib.fib(n);
        System.out.println("--------------------");
        System.out.println(String.format("all(%d) = %d", n, all));
        System.out.println(String.format("incremental(%d) = %d", n, inc));
        System.out.println(String.format("swap(%d) = %d", n, swp));
        System.out.println(String.format("dynamic(%d) = %d", n, dyn));
        System.out.println(String.format("recurrence(%d) = %d", n, rec));
        System.out.println(String.format("parallel dynamic((%d) = %d", n, parDyn));
        MatcherAssert.assertThat(all, is(equalTo(inc)));
        MatcherAssert.assertThat(all, is(equalTo(swp)));
        MatcherAssert.assertThat(all, is(equalTo(dyn)));
        MatcherAssert.assertThat(all, is(equalTo(rec)));
        MatcherAssert.assertThat(all, is(equalTo(parDyn)));
      }
    }
  }

  @Test
  public void testAllEqualFast() {
    int[] values = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 10000 };
    for ( int n : values ) {
      System.out.println("--------------------");

      long now = System.nanoTime();
      BigInteger rec = new FibonacciRecurrence().fib(n);
      long end = System.nanoTime();
      System.out.println(String.format("recurrence(%d) = %d, time = %d mu s", n, rec, (end - now)/1_000));
    }
  }
}
