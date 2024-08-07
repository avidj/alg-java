/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.avidd.recap;

import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author david
 */
public class SortUtils {
  private static final Random rand = new Random(System.currentTimeMillis());

  public static <T> void swap(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }
  
  public static <T> void shuffle(T[] a) {
    for ( int i = 0; i < a.length; i++ ) {
      int pos = rand.nextInt(i, a.length);
      swap(a, i, pos);
    }
  }
  
  public static <T> boolean isSorted(T[] a, Comparator<T> comparator) {
    for ( int i = 1; i < a.length; i++ ) {
      if ( comparator.compare(a[i - 1], a[i]) > 0 ) {
        return false;
      }
    }
    return true;
  }
  
  public static <T> boolean leq(T x, T y, Comparator<T> comparator) {
    return comparator.compare(x, y) <= 0;
  }
  
  public static <T> boolean geq(T x, T y, Comparator<T> comparator) {
    return comparator.compare(x, y) >= 0;
  }
}
