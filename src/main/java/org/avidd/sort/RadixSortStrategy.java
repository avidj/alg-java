package org.avidd.sort;

/**
 * Interface for radix sorts.
 * @author David Kensche
 */
public interface RadixSortStrategy {

  /**
   * Sort an array of strings.
   * @param a the strings to sort
   */
  public void sort(String[] a);

}
