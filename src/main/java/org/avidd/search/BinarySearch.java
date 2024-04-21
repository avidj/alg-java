package org.avidd.search;

import java.util.Comparator;

public class BinarySearch<T> implements ArraySearch<T> {
  private final Comparator<T> comparator;

  public BinarySearch(Comparator<T> aComparator) {
    comparator = aComparator;
  }

  @Override
  public int search(T[] a, T e) {
    int l = 0;
    int r = a.length - 1;
    while ( l <= r ) {
      int m = ( l + r ) / 2;
      int comp = comparator.compare(a[m], e);
      if ( comp == 0 )
        return m;
      else if ( comp > 0 )
        r = m - 1;
      else
        l = m + 1;
    }
    return -1;
  }

}
