package org.avidd.maps;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RWayTrie<V> implements StringSymbolTable<V> {
  // DKE: could be used to enable null values
  private static final Object DISTINCT_NULL = new Object();
  static final int RDX_EXTENDED_ASCII = 256;
  private TrieNode<V> root;
  private boolean containsNull = false;
  private V nullValue;

  @Override
  public void put(String key, V value) {
    if ( key == null ) {
      containsNull = true;
      nullValue = value;
      return;
    }
    root = put(root, key, value, 0);
  }

  private TrieNode<V> put(TrieNode<V> node, String key, V value, int d) {
    if ( node == null ) {
      node = new TrieNode<>();
    }
    if ( d == key.length() ) {
      if ( value == null ) {
        node.value = DISTINCT_NULL;
      } else {
        node.value = value;
      }
    } else {
      final int c = charAt(key, d);
      node.next[c] = put(node.next[c], key, value, d + 1);
    }
    return node;
  }
  
  private static int charAt(String key, int d) {
    byte[] ascii;
    try {
      ascii = key.getBytes("ISO-8859-1");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return ascii[d] + 128; // bytes are signed
   // return ( key.charAt(d) - 0x7F ) + 128;
  }

  @Override
  public boolean contains(String key) {
    if ( key == null ) {
      return containsNull;
    }
    // DKE: Note, that in this form you cannot put null. It would require to
    // "mark" a node.
    // E.g. a distinct null value could be added
    TrieNode<V> node = get(root, key, 0);
    return ( node != null ) && ( node.value != null );
  }

  @SuppressWarnings("unchecked")
  @Override
  public V get(String key) {
    if ( key == null ) {
      return nullValue;
    }
    TrieNode<V> x = get(root, key, 0);
    if ( x == null ) {
      return null;
    }
    return ( x.value != DISTINCT_NULL ) ? (V)x.value : null;
  }

  private TrieNode<V> get(TrieNode<V> node, String key, int d) {
    if ( node == null ) {
      return null;
    }
    if ( d == key.length() ) {
      return node;
    }
    return get(node.next[charAt(key, d)], key, d + 1);
  }

  @Override
  public void delete(String key) {
    if ( key == null ) {
      nullValue = null;
      containsNull = false;
    }
    delete(root, key, 0);
  }

  private boolean delete(TrieNode<V> node, String key, int d) {
    if ( node == null )      return true; 
    if ( d == key.length() ) node.value = null; // if has children, just delete the value
    else {
      final int c = charAt(key, d);
      // if the subtree underneath is empty then delete it
      if ( delete(node.next[c], key, d + 1) ) node.next[c] = null;
      else                                     return false; 
    }
    // we have to check all subtrees whether they are empty
    for ( int i = 0; i < node.next.length; i++ )
      if ( node.next[i] != null ) return false;
    return true;
  }

  @Override
  public Set<String> keySet() {
    // DKE: Not useful to use a HashSet because then we do the hashing which the
    // Trie replaces!
    Set<String> keys = new HashSet<>();
    StringBuilder key = new StringBuilder();
    collect(root, key, keys);
    if ( containsNull ) {
      keys.add(null);
    }
    return keys;
  }

  private void collect(TrieNode<V> node, StringBuilder prefix, Set<String> keys) {
    if ( node == null ) {
      return;
    }
    if ( node.value != null ) {
      keys.add(prefix.toString());
    }
    for ( int c = 0; c < node.next.length; c++ ) {
      StringBuilder copy = new StringBuilder(prefix);
      copy.append((char)(c - 128));
      collect(node.next[c], copy, keys);
    }
  }

  @Override
  public Entry<String, V> floor(String aKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Entry<String, V> ceiling(String aKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int rank(String aKey) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Map<String, V> range(String aKey1, String aKey2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteMin() {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteMax() {
    // TODO Auto-generated method stub

  }

  @Override
  public Set<String> keysWithPrefix(String aPrefix) {
    Set<String> keys = new LinkedHashSet<>();
    TrieNode<V> x = get(root, aPrefix, 0);
    if ( x != null ) {
      if ( x.value != null ) {
        keys.add(aPrefix);
      }
      collect(x, new StringBuilder(aPrefix), keys);
    }
    return Collections.unmodifiableSet(keys);
  }

  @Override
  public String longestPrefix(String query) {
    int l = search(root, query, 0, 0);
    return query.substring(0, l);
  }

  private int search(TrieNode<V> x, String query, int d, int l) {
    if ( x == null )
      return l;
    if ( x.value != null )
      l = d;
    if ( d == query.length() )
      return l;
    int c = charAt(query, d);
    return search(x.next[c], query, d + 1, l);
  }

  @Override
  public Map.Entry<String, V> max() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map.Entry<String, V> min() {
    // TODO Auto-generated method stub
    return null;
  }
}
