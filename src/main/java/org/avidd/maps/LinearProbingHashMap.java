package org.avidd.maps;

import java.util.Map.Entry;
import java.util.Set;

public class LinearProbingHashMap<K, V> implements SymbolTable<K, V> {
  private static final Object DEL = new Object();
  // Typical load factor is 1/2 --> probes for hit ~ 3/2, probes for miss ~ 5/2
  private static final int M = 931;
  private Object[] keys;
  private Object[] vals;

  public LinearProbingHashMap() {
    keys = new Object[M];
    vals = new Object[M];
  }

  @SuppressWarnings("unchecked")
  public V get(K key) {
    for ( int i = hash(key); ( keys[i] != null ); i = ( i + 1 ) % M ) {
      if ( keys[i].equals(key) ) {
        return (V)vals[i];
      }
    }
    return null;
  }

  public void put(K key, V val) {
    int i;
    for ( i = hash(key); ( keys[i] != null ) && ( keys[i] != DEL ); i = ( i + 1 ) % M ) {
      if ( keys[i].equals(key) ) {
        vals[i] = val;
        return;
      }
    }
    keys[i] = key;
    vals[i] = val;
  }

  @Override
  public void delete(K key) {
    for ( int i = hash(key); ( keys[i] != null ) && ( keys[i] != DEL ); i = ( i + 1 ) % M ) {
      if ( keys[i].equals(key) ) {
        keys[i] = DEL;
        vals[i] = null;
      }
    }
  }

  @Override
  public boolean contains(K key) {
    for ( int i = hash(key); ( keys[i] != null ); i = ( i + 1 ) % M ) {
      if ( keys[i].equals(key) ) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<K> keySet() {
    // TODO Auto-generated method stub
    return null;
  }

  private int hash(K key) {
    // return key.hashCode() % M; // can be negative
    // return Math.abs(key.hashCode()) % M; // int -2^31 ... 2^31
    return ( key.hashCode() & Integer.MAX_VALUE ) % keys.length;
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
}
