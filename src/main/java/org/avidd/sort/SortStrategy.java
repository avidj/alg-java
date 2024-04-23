package org.avidd.sort;

import java.util.Comparator;

public interface SortStrategy {

  public <T> void sort(T[] a, Comparator<? super T> comp);

}
