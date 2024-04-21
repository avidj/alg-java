package org.avidd.maps;

import java.util.Map;
import java.util.Set;

public interface SymbolTable<K, V> {

  public void put(K key, V value);

  public V get(K key);

  public void delete(K key);

  public Set<K> keySet();

  boolean contains(K key);

  /**
   * @param aKey
   * @return the largest entry with key smaller or equal aKey
   */
  Map.Entry<K, V> floor(K key);

  /**
   * @param aKey
   * @return the smallest entry with key larger or equal aKey
   */
  Map.Entry<K, V> ceiling(K key);

  /**
   * @return the maximal entry in the map
   */
  Map.Entry<K, V> max();

  /**
   * @return the minimal entry in the map
   */
  Map.Entry<K, V> min();

}
