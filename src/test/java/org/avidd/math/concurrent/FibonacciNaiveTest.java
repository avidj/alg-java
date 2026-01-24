package org.avidd.math.concurrent;

import java.math.BigInteger;
import org.avidd.math.Fibonacci;
import org.avidd.math.FibonacciNaive;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class FibonacciNaiveTest {
  
  @Test
  public void testFib10() {
    int n = 10;
    BigInteger expected = BigInteger.valueOf(55);
    BigInteger result = fib().fib(n);
    System.err.println("Fib(" + n + ") = " + result);
    MatcherAssert.assertThat(result, is(equalTo(expected)));
  }

  public Fibonacci fib() {
    return new FibonacciNaive();
  }
}
