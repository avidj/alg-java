package org.avidd.queues;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class IndexedMaxHeap<T> implements IndexedPriorityQueue<T> {

  private final Comparator<T> comparator;
  private final T[] keys; // priority of i
  private final int[] pq; // index of the key in heap position i
  private final int[] qp; // heap position of the key with index i
  private int size;

  @SuppressWarnings("unchecked")
  public IndexedMaxHeap(Comparator<T> aComparator, int size) {
    comparator = aComparator;
    keys = (T[])new Object[size + 1];
    pq = new int[size + 1];
    qp = new int[size + 1];
    Arrays.fill(pq, -1);
    Arrays.fill(qp, -1);
    size = 0;
  }

  @Override
  public void insert(int index, T element) {
    if ( index > keys.length - 1 ) {
      throw new IndexOutOfBoundsException("index = " + index);
    }
    size++;
    keys[index] = element;
    pq[size] = index;
    qp[index] = size;
    swim(size);
  }

  @Override
  public boolean contains(int index) {
    return ( keys[index] != null );
  }

  @Override
  public void update(int index, T element) {
    keys[index] = element;
    // TODO: swim is only correct if this is increaseKey
    swim(qp[index]);
  }

  private void swim(int k) {
    while ( k > 1 && less(pq[k / 2], pq[k]) ) {
      swap(qp, pq[k], pq[k / 2]);
      swap(pq, k, k / 2);
      k = k / 2;
    }
  }

  private void sink(int k) {
    while ( 2 * k <= size ) { // until end of heap is reached
      int j = 2 * k; // j is the first son of k
      if ( j < size && less(pq[j], pq[j + 1]) ) {
        j++;
      } // if j is not last element choose largest son
      if ( !less(pq[k], pq[j]) ) {
        break;
      } // stop if k is not smaller than its largest son
      swap(qp, pq[k], pq[j]);
      swap(pq, k, j); // else sap k with its largest son
      k = j; // and continue sinking at that position
    }
  }

  private boolean less(int i, int j) {
    assert ( keys[i] != null );
    assert ( keys[j] != null );
    return ( comparator.compare(keys[i], keys[j]) < 0 );
  }

  @Override
  public Map.Entry<Integer, T> pop() {
    if ( size == 0 ) {
      throw new IllegalStateException();
    }
    int id = pq[1];
    T max = keys[pq[1]];
    swap(qp, pq[1], pq[size]);
    swap(pq, 1, size); // move the popped element to the end
    keys[pq[size]] = null; // to prevent memory leaks!
    qp[pq[size]] = -1;
    pq[size] = -1;
    size--;
    sink(1);
    return new Entry(id, max);
  }

  private static void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  @Override
  public Map.Entry<Integer, T> peek() {
    return new Entry(pq[1], keys[1]);
  }

  @Override
  public Map.Entry<Integer, T> peek(int i) {
//    return new Entry(pq[i], keys[i]);
    return new Entry(qp[i], keys[i]);
  }

  @Override
  public boolean isEmpty() {
    return ( size == 0 );
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder("MaxHeap( size = ").append(size).append(", \n");
    StringBuilder iString = new StringBuilder();
    StringBuilder keysString = new StringBuilder();
    StringBuilder pqString = new StringBuilder();
    StringBuilder qpString = new StringBuilder();
    // if ( size > 0 ) {
    iString.append(0);
    keysString.append(keys[0]);
    pqString.append(pq[0]);
    qpString.append(qp[0]);
    // }
    for ( int i = 1; i < keys.length; i++ ) {
      iString.append("\t\t").append(i);
      keysString.append("\t\t").append(keys[i]);
      pqString.append("\t\t").append(pq[i]);
      qpString.append("\t\t").append(qp[i]);
    }
    return string.append("i:\t").append(iString).append("\n").append("keys:\t").append(keysString)
        .append("\n").append("pq:\t").append(pqString).append("\n").append("qp:\t")
        .append(qpString).append("\n").toString();
  }

  private class Entry implements Map.Entry<Integer, T> {
    private final Integer key;
    private final T value;

    Entry(Integer aKey, T aValue) {
      key = aKey;
      value = aValue;
    }

    @Override
    public Integer getKey() {
      return key;
    }

    @Override
    public T getValue() {
      return value;
    }

    @Override
    public T setValue(T value) {
      throw new UnsupportedOperationException();
    }
  }
}
