package org.avidd.queues;

import java.util.Collection;
import java.util.Comparator;

public class MinHeap<T> implements PriorityQueue<T> {
  private final PriorityQueue<T> maxPQ;

  public MinHeap(Comparator<T> aComparator, int size) {
    maxPQ = new MaxHeap<>(new InvertedComparator<>(aComparator), size);
  }

  public MinHeap(Comparator<T> aComparator, Collection<T> elements) {
    maxPQ = new MaxHeap<>(new InvertedComparator<>(aComparator), elements);
  }

  @Override
  public void insert(T element) {
    maxPQ.insert(element);
  }

  @Override
  public T pop() {
    return maxPQ.pop();
  }

  @Override
  public T peek() {
    return maxPQ.peek();
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
  public void addAll(Collection<T> collection) {
    maxPQ.addAll(collection);
  }

  @Override
  public String toString() {
    return new StringBuilder("MinHeap(").append(maxPQ.toString()).append(")").toString();
  }
}
