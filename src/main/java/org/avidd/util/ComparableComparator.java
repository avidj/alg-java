package org.avidd.util;

import java.util.Comparator;

/**
 * A comparator for use with comparables.
 * 
 * @author David Kensche
 *
 * @param <T> the type of comparables handled by this comparator 
 */
public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

  @Override
  public int compare(T o1, T o2) {
    return o1.compareTo(o2);
  }

}
