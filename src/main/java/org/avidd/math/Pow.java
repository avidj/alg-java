package org.avidd.math;

public class Pow {

  public long pow(int b, int e) {
    long result = 1;
    while ( e != 0 ) {
      if ( ( e & 1 ) != 0 ) {
        result *= b;
      }
      e >>= 1;
      b *= b;
    }
    return result;
  }
}
