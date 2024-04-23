package org.avidd.sort;

/**
 * Modified merge sort. During the merge step this algorithm does not only sort but also counts the
 * inversions. Since all keys in the left array are smaller than those in the right array even when
 * sorted, you can count the inversions. During the merge step the algorithm iterates the partial
 * arrays from right to left. Therefore, fewer cases must be considered when incrementing the inv
 * counter: When the left key is larger than the right key then it is also larger than all other 
 * keys before the right key in the right sub array.
 */
public class CountInversions {

  public int count(int[] a) {
    return countInversions(a, 0, a.length - 1);
  }

  private int countInversions(int[] a, int l, int r) {
    int sum = 0;
    if ( l < r ) {
      int m = ( l + r ) / 2;
      int invL = countInversions(a, l, m);
      int invR = countInversions(a, m + 1, r);
      // The count of inversions in the subarray a[l..r] is exactly the sum of inversions in the 
      // subarrays a[l..m], a[m+1..r] + the number of keys in a[l..m] that are greater than other
      // keys in a[m+1..r]. An array with one element has no inversions.
      sum = invL + invR + merge(a, l, m, r);
    }
    return sum;
  }

  private int merge(int[] a, int l, int m, int r) {
    int[] b = new int[r - l + 1];
    int i = r - l;
    int j = m;
    int k = r;
    int inv = 0;
    while ( ( j >= l ) && ( k > m ) ) {
      if ( a[j] <= a[k] ) {
        b[i--] = a[k--];
      } else {
        // On the right, (k-m) elements are smaller than a[j], increment inv by (k-m).
        inv += ( k - m );
        b[i--] = a[j--];
      }
    }
    while ( j >= l ) { b[i--] = a[j--]; }
    while ( k > m )  { b[i--] = a[k--]; }
    for ( i = l; i <= r; i++ ) { a[i] = b[i - l]; }
    return inv;
  }

//  private int otherMerge(int[] a, int l, int m, int r) {
//    int[] b = new int[r - l + 1];
//    int i = 0;
//    int j = l;
//    int k = m + 1;
//    int inv = 0;
//    while ( ( j <= m ) && ( k <= r ) ) {
//      if ( a[j] <= a[k] ) {
//        b[i++] = a[j++];
//      } else {
//        // On the right (m - j + 1) elements are smaller than a[j], increment inv by (k-m).
//        inv += ( m - j + 1 );
//        b[i++] = a[k++];
//      }
//    }
//    while ( j <= m ) { b[i++] = a[j++]; }
//    while ( k <= r ) { b[i++] = a[k++]; }
//    for ( i = l; i <= r; i++ ) { a[i] = b[i - l]; }
//    return inv;
//  }
}
