package org.avidd.math.concurrent;

import java.math.BigInteger;
import org.avidd.math.Fibonacci;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public abstract class AbstractFibonacciTest {
  
  public abstract Fibonacci fib();

  @Test
  public void testFib10() {
    int n = 10;
    BigInteger expected = BigInteger.valueOf(55);
    BigInteger result = fib().fib(n);
    System.err.println("Fib(" + n + ") = " + result);
    MatcherAssert.assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testFib20() {
    int n = 20;
    BigInteger expected = BigInteger.valueOf(6765);
    BigInteger result = fib().fib(n);
    System.err.println("Fib(" + n + ") = " + result);
    MatcherAssert.assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testFib30() {
    int n = 30;
    BigInteger expected = BigInteger.valueOf(832040);
    BigInteger result = fib().fib(n);
    System.err.println("Fib(" + n + ") = " + result);
    MatcherAssert.assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void testFib40() {
    int n = 40;
    BigInteger expected = BigInteger.valueOf(102334155);
    BigInteger result = fib().fib(n);
    System.err.println("Fib(" + n + ") = " + result);
    MatcherAssert.assertThat(result, is(equalTo(expected)));
  }
}
