package org.avidd.queues;

import java.util.Comparator;
import java.util.Map;

public class IndexedMinHeap<T> implements IndexedPriorityQueue<T> {
  private final IndexedPriorityQueue<T> maxPQ;

  public IndexedMinHeap(Comparator<T> aComparator, int size) {
    maxPQ = new IndexedMaxHeap<T>(new InvertedComparator<T>(aComparator), size);
  }

  @Override
  public void insert(int i, T element) {
    maxPQ.insert(i, element);
  }

  @Override
  public boolean contains(int i) {
    return maxPQ.contains(i);
  }

  @Override
  public void update(int i, T element) {
    maxPQ.update(i, element);
  }

  @Override
  public Map.Entry<Integer, T> pop() {
    return maxPQ.pop();
  }

  @Override
  public Map.Entry<Integer, T> peek() {
    return maxPQ.peek();
  }

  @Override
  public Map.Entry<Integer, T> peek(int i) {
    return maxPQ.peek(i);
  }

  @Override
  public boolean isEmpty() {
    return maxPQ.isEmpty();
  }

  @Override
  public int size() {
    return maxPQ.size();
  }

  private static class InvertedComparator<T> implements Comparator<T> {
    private final Comparator<T> comparator;

    InvertedComparator(Comparator<T> aComparator) {
      comparator = aComparator;
    }

    @Override
    public int compare(T o1, T o2) {
      return -comparator.compare(o1, o2);
    }

  }

  @Override
  public String toString() {
    return new StringBuilder("MinHeap(").append(maxPQ.toString()).append(")").toString();
  }
}
