package org.avidd.maps;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A patricia tree storing suffixes of keys.
 * 
 * Operations Suffix Infix = Prefix of a Suffix longest repeated sub string
 * longest common sub string longest palindromic sub string
 * 
 * Applications String search computational biology
 * 
 * @author David Kensche
 * 
 * @param <V>
 */
public class SuffixTree<V> implements StringSymbolTable<V> {

  @Override
  public void put(String key, V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public V get(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void delete(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<String> keySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<String, V> max() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<String, V> min() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<String, V> floor(String aKey) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<String, V> ceiling(String aKey) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int rank(String aKey) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, V> range(String aKey1, String aKey2) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteMin() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteMax() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<String> keysWithPrefix(String aPrefix) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String longestPrefix(String query) {
    throw new UnsupportedOperationException();
  }
}
