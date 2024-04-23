package org.avidd.sort;

import java.util.Comparator;
import java.util.Stack;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

class QuickSortStack implements SortStrategy {
  
  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {
    Stack<Integer> stack = new Stack<>();
    int l = 0;
    int r = a.length - 1;
    int l2;
    int r2;
    T pivot;
    stack.push(l);
    stack.push(r);
    while ( !stack.empty() ) {
      r = stack.pop();
      l = stack.pop();
      do {
        pivot = a[ ( l + r ) / 2];
        l2 = l;
        r2 = r;
        do {
          while ( less(a[l2], pivot, comparator) ) { // scan left partition
            l2 = l2 + 1;
          }
          while ( less(a[r2], pivot, comparator) ) { // scan right partition
            r2 = r2 - 1;
          }
          if ( l2 <= r2 ) {
            if ( l2 != r2 ) {
              swap(a, l2, r2);
            }
            l2 = l2 + 1;
            r2 = r2 - 1;
          }
        } while ( l2 < r2 );
        if ( r2 - l > r - l2 ) { // is left side piece larger?
          if ( l < r2 ) {
            stack.push(l);
            stack.push(r2);
          }
          l = l2;
        } else {
          if ( l2 < r ) { // if left side isn't, right side is larger
            stack.push(l2);
            stack.push(r);
          }
          r = r2;
        }
      } while ( l < r );
    }
  }
}
