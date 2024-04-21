package org.avidd.math.concurrent;

public class Even {
  private int n = 0;

  public int next() {
    n++;
    n++;
    assert ( n % 2 == 0 ); // postcondition
    return n;
  }
}
