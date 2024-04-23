package org.avidd.sort;

import java.util.Stack;

class StraightRadix implements RadixSortStrategy {
  private static final int ZERO = 'a';
  private static final int RADIX = 27;
  @SuppressWarnings({"unchecked"})
  private final Stack<String>[] stacks = (Stack<String>[])new Stack[RADIX];

  @Override
  public void sort(String[] a) {
    for ( int i = 0; i < RADIX; i++ ) {
      stacks[i] = new Stack<>();
    }
    int l = 0;
    for ( int i = 0; i < a.length; i++ ) {
      if ( a[i].length() > l )
        l = a[i].length();
    }
    for ( int pos = l - 1; pos >= 0; pos-- ) {
      for ( int i = 0; i < a.length; i++ ) {
        if ( a[i].length() > pos ) {
          stacks[a[i].charAt(pos) - ZERO + 1].push(a[i]);
        } else {
          stacks[0].push(a[i]);
        }
      }
      int c = a.length - 1;
      for ( int i = stacks.length - 1; i >= 0; i-- ) {
        while ( !stacks[i].empty() )
          a[c--] = stacks[i].pop();
      }
    }
  }
}
