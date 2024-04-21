package org.avidd.search;

public class InvertedBinarySearch {

  public int search(int[] a, int e) {
    int low = 0;
    int high = a.length - 1;
    while ( low <= high ) {
      int mid = ( high + low ) / 2;
      if ( e == a[mid] )
        return mid;
      else if ( e > a[mid] )
        high = mid - 1;
      else
        low = mid + 1;
    }
    return -1;
  }

}
