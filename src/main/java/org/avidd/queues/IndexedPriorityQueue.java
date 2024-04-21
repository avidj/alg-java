package org.avidd.queues;

import java.util.Map;

public interface IndexedPriorityQueue<T> {

  /**
   * @param i
   *          the index
   * @param element
   *          the element to associate with the index
   */
  public abstract void insert(int i, T element);

  /**
   * @param i
   *          the key to query
   * @return true iff the heap contains an element with index i
   */
  public abstract boolean contains(int i);

  /**
   * @param i
   *          the index of the element to decrease
   * @param element
   *          the element that is to be updated
   */
  public void update(int i, T element);

  /**
   * Return and delete top element.
   * 
   * @return the top entry
   */
  public abstract Map.Entry<Integer, T> pop();

  /**
   * @return the top entry
   */
  public abstract Map.Entry<Integer, T> peek();

  /**
   * @param index
   *          to peek
   * @return the element with that index if it exists
   */
  public abstract Map.Entry<Integer, T> peek(int i);

  /**
   * @return true iff empty
   */
  public abstract boolean isEmpty();

  /**
   * @return the number of entries
   */
  public abstract int size();

}