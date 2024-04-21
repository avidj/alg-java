package org.avidd.queues;

import java.util.Collection;

public interface PriorityQueue<T> {

  public abstract void insert(T element);

  public abstract T pop();

  public abstract T peek();

  public abstract boolean isEmpty();

  public abstract int size();

  void addAll(Collection<T> collection);

}