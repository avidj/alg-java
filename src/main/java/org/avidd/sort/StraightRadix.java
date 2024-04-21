package org.avidd.sort;

import java.util.Stack;

class StraightRadix implements RadixSortStrategy {
  private static final int ZERO = 'a';
  private static final int RADIX = 27;
  @SuppressWarnings({"unchecked"})
  private Stack<String>[] stacks = (Stack<String>[])new Stack[RADIX];

  @Override
  public void sort(String[] a) {
    for ( int i = 0; i < RADIX; i++ ) {
      stacks[i] = new Stack<String>();
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
      // algorithm(a, i, 0, a.length);
    }
  }

  /*
   * private void algorithm(String[] a, int pos, int start, int end) { for(int i
   * = start; i < end; i++) { if(a[i].length() > pos) { stacks[a[i].charAt(pos)
   * - ZERO + 1].push(a[i]); } else { stacks[0].push(a[i]); } } int c = a.length
   * - 1; for(int i = stacks.length-1; i >= 0; i--) { while(!stacks[i].empty())
   * a[c--] = (String)stacks[i].pop(); } }
   */
  /*
   * public void sort(String[] a) { int[] cs = new int[RADIX]; int l = 0;
   * for(int i = 0; i < a.length; i++) { if(a[i].length() > l) l =
   * a[i].length(); }
   * 
   * for(int pos = l - 1; pos >= 0; pos--) { for(int i = 0; i < a.length; i++) {
   * if(a[i].length() > pos) { cs[a[i].charAt(pos) - ZERO + 1]++; } else {
   * cs[0]++; } } for(int i = 1; i < RADIX; i++) { cs[i] = cs[i-1] + cs[i]; }
   * int[] is = new int[a.length]; int c = a.length - 1; for(int i =
   * stacks.length-1; i >= 0; i--) { is[cs[a[i].charAt(pos) - ZERO + 1]] = a[i];
   * while(!stacks[i].empty()) a[c--] = (String)stacks[i].pop(); } } }
   */

}
