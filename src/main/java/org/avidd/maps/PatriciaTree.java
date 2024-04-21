package org.avidd.maps;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Applications DB Search P2P Network search IP routing tables Compressed
 * quad-tree for N-body simulations efficient storage of XML
 * 
 * @author David Kensche
 * 
 * @param <V> the value class for this symbol table
 */
public class PatriciaTree<V> implements StringSymbolTable<V> {
  private static final Logger LOG = LogManager.getLogger(PatriciaTree.class);
  private static final Object NULL = new Object();
  private Node root;
  private Object emptyKeyValue;
  private Object nullValue;

  @Override
  public void put(String key, V value) {
    if ( key == null ) {
      if ( value == null ) { nullValue = NULL; } 
      else                 { nullValue = value; }
      return;
    }
    if ( key.length() == 0 ) {
      if ( value == null ) { emptyKeyValue = NULL; }
      else                 { emptyKeyValue = value; }
      return;
    }
    root = put(root, key, value, 0);
  }
  
  private Node put(Node x, String key, Object val, int d) {
    if ( x == null ) { 
      x = new Node(key.substring(d));
      return x;
    }
    final char c = key.charAt(d);
    if      ( c < x.chars[0] ) { x = put(x.left, key, val, d + 1); }
    else if ( c > x.chars[0] ) { x = put(x.right, key, val, d + 1); }
    else if ( d < key.length() - 1 ) {
      int i = 0;
      while ( i < x.chars.length && 
              i < key.length() && 
              x.chars[i] == key.charAt(d + i) ) { 
        i++; 
      }
      LOG.info(String.format("chars[0, %1$d] == key[%2$d, %3$d]", i, d, d + i));
      
      // find the longest common prefix of key and x.chars
      // if the LCP is complete but shorter than the remainder of key add a middle node
      // if the LCP and the remainder are are of the same length, set the value
      // if the LCP is shorter than chars, add create two new nodes (left or right) and middle
      // if
    }
    else { x.val = val; }
    return x;
  }

  @Override
  public V get(String key) {
    if ( key == null ) { 
      if ( nullValue != NULL ) {
        @SuppressWarnings("unchecked")
        V temp = (V)nullValue;
        return temp; 
      } else { 
        return null; 
      } 
    }
    if ( key.length() == 0 ) { 
      if ( emptyKeyValue != NULL ) {
        @SuppressWarnings("unchecked")
        V temp = (V)emptyKeyValue;
        return temp;
      } else { 
        return null; 
      } 
    }
    Node node = get(root, key, 0);
    if ( node != null && node.val != null && node.val != NULL ) {
      @SuppressWarnings("unchecked")
      V temp = (V)node.val;
      return temp;
    }
    return null;
  }

  private Node get(Node x, String key, int d) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean contains(String key) {
    if ( key == null )       { return ( nullValue != null ); }
    if ( key.length() == 0 ) { return ( emptyKeyValue != null ); }
    Node node = get(root, key, 0);
    return ( node != null ) && ( node.val != null );
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
  
  private static class Node {
    char[] chars;
    Node left;
    Node middle;
    Node right;
    Object val;
    
    Node() {
      chars = new char[0];
    }
    
    Node(char c) {
      chars = new char[1];
      chars[0] = c;
    }
    
    Node(char[] aChars) {
      chars = aChars;
    }
    
    public Node(String substring) {
      chars = substring.toCharArray();
    }

    @Override
    public String toString() {
      return new StringBuilder("Node(")
        .append("chars = ")
        .append(new String(chars))
        .append(")")
        .toString();
    }
  }
}
