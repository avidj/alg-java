package org.avidd.queues;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

// TODO: add resizing of the heap as it shrinks (but only until initial size) and grows
// TODO: make it thread safe
public class MaxHeap<T> implements PriorityQueue<T> {
  private final Comparator<T> comparator;
  private final T[] heap;
  private int size;

  public MaxHeap(Comparator<T> aComparator, int size) {
    comparator = aComparator;
    @SuppressWarnings("unchecked")
    T[] temp = (T[])new Object[size + 1];
    heap = temp;
    size = 0;
  }

  public MaxHeap(Comparator<T> aComparator, Collection<T> elements) {
    comparator = aComparator;
    @SuppressWarnings("unchecked")
    T[] temp = (T[])new Object[elements.size() + 1];
    heap = temp;
    size = elements.size();
    int i = 1;
    for ( T t : elements ) {
      heap[i++] = t;
    }
    // The leaves are already heaps, this operation makes the other subtrees
    // heaps as well.
    for ( int j = size / 2; j >= 1; j-- ) {
      sink(j);
    }
  }

  @Override
  public void insert(T element) {
    if ( size == heap.length - 1 )//TODO resize
      throw new IllegalStateException("overflow");
    size++;
    heap[size] = element;
    swim(size);
  }

  private void swim(int k) {
    while ( k > 1 && less(k / 2, k) ) {
      swap(heap, k, k / 2);
      k = k / 2;
    }
  }

  @Override
  public T pop() {
    if ( size == 0 ) {
      throw new NoSuchElementException();
    }
    T max = heap[1];
    swap(heap, 1, size);
    heap[size--] = null; // to prevent memory leaks!
    sink(1);
    return max;
  }

  private void sink(int k) {
    while ( 2 * k <= size ) { // until end of heap is reached
      int j = 2 * k; // j is the first son of k
      if ( j < size && less(j, j + 1) ) {
        j++;
      } // if j is not last element choose largest son
      if ( !less(k, j) ) {
        break;
      } // stop if k is not smaller than its largest son
      swap(heap, k, j); // else swap k with its largest son
      k = j; // and continue sinking at that position
    }
  }

  private boolean less(int i, int j) {
    return ( comparator.compare(heap[i], heap[j]) < 0 );
  }

  private void swap(T[] a, int i, int j) {
    T temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  @Override
  public T peek() {
    return heap[1];
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
  public void addAll(Collection<T> collection) {
    for ( T t : collection ) {
      insert(t);
    }
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder("MaxHeap( size = ").append(size).append(", heap [");
    if ( size > 0 ) {
      string.append(heap[1]);
    }
    for ( int i = 2; i < size + 1; i++ ) {
      string.append(", ").append(heap[i]);
    }
    return string.append("] )").toString();
  }
}
