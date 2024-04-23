package org.avidd.sort;

/**
 * Strategy for integer array sorts.
 */
public interface IntSortStrategy {

  /**
   * @param toSort the array to sort
   */
  public void sort(int[] toSort);
}
