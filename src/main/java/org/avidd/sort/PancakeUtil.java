package org.avidd.sort;

/**
 * Utilities for pancake sort.
 */
final class PancakeUtil {

  /**
   * @param stack the complete list to flip pancakes on the top
   * @param newTop the pancake that will be the top after flipping 
   * @param count the number of pancakes to be flipped, i.e., the position of newTop
   * @return the new top node, i.e., the complete pancake stack
   */
  static NaiveNode flip(final NaiveNode stack, final NaiveNode newTop, int count) {
    assert ( stack.prev == null ) : "list must be the current top of the stack";
    System.out.println("flip " + count);
    assert ( count > -1 ) : "Cannot flip a negative number of pancakes.";
    if ( count == 1 ) {
      System.out.println("WARN: no use in flipping one pancake");
      return stack;
    }
    
    NaiveNode tail = newTop.next;
    NaiveNode x = newTop;
    if ( tail != null ) {
    	  tail.prev = stack;
    }
    while ( x != null ) {
    	  NaiveNode prev = x.prev;
      x.prev = x.next;
      x.next = prev;
      x = prev;
    }
    newTop.prev = null;
    stack.next = tail;
    assert PancakeUtil.isList(newTop);
    return newTop;
  }

  /** 
   * @param list the list to test
   * @return {@code true} if the list is a sorted tail, i.e., ignores all preceding nodes
   */
  static <T extends Node<T>>boolean sortedTail(Node<T> list) {
    if ( list.next() == null ) { return true; }
    for ( Node<T> x = list.next(); x != null; x = x.next() ) {
      if ( x.prev().value() > x.value() ) { return false; }
    }
    return true;
  }
  
  /**
   * @param an array of integers
   * @return a doubly-linked list of integers in the same order
   */
  static NaiveNode toNaiveList(int[] a) {
    int[] est = estimateInversions(a);
    NaiveNode list = new NaiveNode(a[a.length - 1], est[a.length - 1]);
    for ( int i = a.length - 2; i >= 0; i-- ) {
      list = new NaiveNode(a[i], est[i], list);
    }
    return list;
  }
  
  static <T extends Node<T>> void toArray(int[] a, T stack) {
	for ( int i = 0; stack != null; stack = stack.next() ) {
	  a[i++] = stack.value();
	}
  }

  /**
   * @param an array of integers
   * @return an estimation of the number of inversions, the sum in the array will be greater or 
   *   equal the actual number of inversions
   */
  static int[] estimateInversions(int[] a) {
    int[] est = new int[a.length];
    int cur = a[0];    
    int inv = 0;
    est[0] = inv;
    for ( int i = 1; i < a.length; i++ ) {
      if ( a[i] < cur ) { 
        inv += 1;
        est[i] = inv;
      } else if ( a[i] == cur ) {
        est[i] = inv;
      } else {
        inv = 0;
      }
    }
    return est;
  }

  static void printPancakes(NaiveNode list) {
    StringBuilder string = new StringBuilder();
    string.append("\nest-ori\tvalue\tpancake");
    for ( NaiveNode x = list; x != null; x = (NaiveNode)x.next() ) {
      string.append("\n");
      string.append(x.est).append('\t').append(x.value()).append('\t');
      nTimes(string, x.value(), '*');      
    }
    string.append("\n");
    System.out.print(string);;
  }

  static <T extends Node<T>> void printPancakes(T list) {
    StringBuilder string = new StringBuilder();
    string.append("\nest-ori\tvalue\tpancake");
    for ( T x = list; x != null; x = (T)x.next() ) {
      string.append("\n");
      string.append(x.value()).append('\t');
      nTimes(string, x.value(), '*');      
    }
    string.append("\n");
    System.out.print(string);;
  }

  /**
   * @param top 
   * @return
   */
  static <T extends Node<T>> boolean isList(T top) {
    if ( top.prev() != null ) {
      throw new IllegalArgumentException("Expect top node as input.");
    }
    for ( T x = top; x != null; x = x.next() ) {
      if ( x.next() != null && x.next().prev() != x ) { 
        return false;
      }
    }
    return true;
  }
  
  private static void nTimes(StringBuilder string, int n, char c) {
    for ( int i = 0; i < n; i++ ) { string.append(c); }
  }

  
  static <T extends Node<T>> String toString(T stack) {
    StringBuilder s = new StringBuilder();
    append(stack, s);
    return s.toString();
  }
  
  private static <T extends Node<T>> void append(T x, StringBuilder s) {
    s.append(x.value()).append('\t');
    if ( x.next() != null ) {
      append(x.next(), s);
    }
  }

  interface Node<T extends Node<T>> {
    
    T next();
    
    T prev();
    
    int value();
    
  }
  
  static class EfficientNode implements Node<EfficientNode> {
    int value;
    int direction = 1; // one of 1 and 0
    EfficientNode[] neighbors = new EfficientNode[2];
    
    EfficientNode(int value, EfficientNode next) {
      this.value = value;
      this.neighbors[nextIndex()] = next;
      next.neighbors[next.prevIndex()] = this;
    }
    
    private int nextIndex() {
      return 1 - direction;
    }
    
    private int prevIndex() {
      return direction;
    }
    
    @Override
    public EfficientNode next() {
      return neighbors[nextIndex()];
    }

    @Override
    public EfficientNode prev() {
      return neighbors[prevIndex()];
    }

    @Override
    public int value() {
      return value;
    }

    @Override
    public String toString() {
      return PancakeUtil.toString(this);
    }
  }
  
  /**
   * A class for nodes in a doubly linked list. Values of type integer
   */
  static class NaiveNode implements Node<NaiveNode> {
    /** The value of that node. */
    final int value;
    
    /** The estimated number of inversions of that node */
    int est;
    NaiveNode next;
    NaiveNode prev;
    
    NaiveNode(int aValue, int aEst) {
      value = aValue;
      est = aEst;
    }

    NaiveNode(int aValue, int aEst, NaiveNode aNext) {
      value = aValue;
      est = aEst;
      next = aNext;
      next.prev = this;
    }
    
    public NaiveNode next() {
      return next;
    }
    
    public NaiveNode prev() {
      return prev;
    }
    
    public int value() {
      return value;
    }
    
    @Override
    public String toString() {
      return PancakeUtil.toString(this);
    }
  }
}
