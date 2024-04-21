package org.avidd.maps;

import java.util.Map.Entry;
import java.util.Set;

public class SeparateChainingHashMap<K, V> implements SymbolTable<K, V> {
  // Typical load factor is 5 --> M = N / 5 --> constant-time ops
  private static final int M = 931;
  private Node<K, V>[] st;

  @SuppressWarnings({"unchecked"})
  public SeparateChainingHashMap() {
    st = new Node[M];
  }

  @Override
  public V get(K key) {
    int hash = hash(key);
    for ( Node<K, V> x = st[hash]; x != null; x = x.next ) {
      if ( ( x.key == key ) || x.key.equals(key) ) {
        return x.val;
      }
    }
    return null;
  }

  @Override
  public void put(K key, V val) {
    int hash = hash(key);
    for ( Node<K, V> x = st[hash]; x != null; x = x.next ) {
      if ( ( x.key == key ) || x.key.equals(key) ) {
        x.val = val;
        return;
      }
    }
    st[hash] = new Node<K, V>(key, val, st[hash]);
  }

  @Override
  public void delete(K key) {
    int hash = hash(key);
    Node<K, V> previous = null;
    for ( Node<K, V> x = st[hash]; x != null; x = x.next ) {
      if ( x.key.equals(key) ) {
        if ( previous == null ) {
          st[hash] = x.next;
        } else {
          previous.next = x.next;
        }
        return;
      }
      previous = x;
    }
  }

  @Override
  public boolean contains(K key) {
    int hash = hash(key);
    for ( Node<K, V> x = st[hash]; x != null; x = x.next ) {
      if ( x.key.equals(key) ) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<K> keySet() {
    throw new UnsupportedOperationException();
  }

  private int hash(K key) {
    // return key.hashCode() % M; // can be negative
    // return Math.abs(key.hashCode()) % M; // int -2^31 ... 2^31
    if ( key == null ) {
      return 0;
    }
    return ( key.hashCode() & Integer.MAX_VALUE ) % st.length;
  }

  @Override
  public Entry<K, V> floor(K key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<K, V> ceiling(K key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<K, V> max() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Entry<K, V> min() {
    throw new UnsupportedOperationException();
  }

  static class Node<K, V> {
    final K key;
    V val;
    Node<K, V> next;

    Node(K aKey, V aVal, Node<K, V> aNext) {
      key = aKey;
      val = aVal;
      next = aNext;
    }
  }
}
