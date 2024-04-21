package org.avidd.math;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class FibonacciRecurrence implements Fibonacci {
  private static final BigDecimal SQRT_5 = BigDecimal.valueOf(Math.sqrt(5.0));
  private static final BigDecimal PHI = BigDecimal.valueOf(1.0 + Math.sqrt(5.0) / 2.0);
  private static final BigDecimal PHI_D = BigDecimal.valueOf(1.0 - Math.sqrt(5.0) / 2.0);

  public BigInteger fib(int n) {
    if ( n < 0 ) {
      throw new IllegalArgumentException();
    }
    if ( n <= 1 ) {
      return BigInteger.valueOf(n);
    }
    return pow(PHI, n).divide(SQRT_5).subtract(pow(PHI_D, n).divide(SQRT_5)).toBigInteger();
  }

  private static BigDecimal pow(BigDecimal x, int n) {
    BigDecimal pow = x;
    for ( int i = 1; i < n; i++ ) {
      pow = pow.multiply(x);
    }
    return pow;
  }
}
