package org.avidd.maps;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A ternary tree is a much more memory-efficient variant of R-way Trie. It
 * gains its efficiency through reducing the fan-out of nodes from the radix R
 * to 3. Each node has a left subtree of smaller nodes, a right subtree of
 * larger nodes and the middle subtree consisting of exactly the node
 * corresponding to the single one character referenced by it and its subtrees.
 * This tremendously reduces the number of null links that waste space in R-way
 * Tries.
 * 
 * @param <V> the value type
 */
public class TernaryTree<V> implements StringSymbolTable<V> {
  /*
   * The distinct null must be used if someone puts a null value into the trie.
   * The effect shall be: When putting (key, null), the tree shall put (key,
   * DISTINCT_NULL) instead. When getting (key, DISTINCT_NULL), the tree shall
   * return null instead. When asking contains(key) the tree shall return true
   * if (key, DISTINCT_NULL) is contained and false if key is not contained at
   * all. We need the DISTINCT_NULL because it is not unusual for trie nodes to
   * have no value. Just imagine an empty trie with ("key", some-object) put
   * into it.
   */
  private static final Object NULL = new Object();
  private TstNode root;
  private Object emptyValue;
  private Object nullValue;

  public boolean isEmpty() {
    // TODO: test
    return root == null && emptyValue == null && nullValue == null;
  }
  
  @Override
  public void put(String key, V value) {
    if ( key == null ) {
      if ( value == null ) { nullValue = NULL; } 
      else                 { nullValue = value; }
      return;
    }
    if ( key.length() == 0 ) {
      if ( value == null ) { emptyValue = NULL; }
      else                 { emptyValue = value; }
      return;
    }
    root = put(null, root, key, value, 0);
  }

  /**
   * The put method is called with the node to put a value into. It is called
   * recursively and, in doing so, updates the current branches of nodes on the
   * path.
   * 
   * @param node
   *          the node to put the value into
   * @param key
   *          the key to put
   * @param value
   *          the value to put
   * @param d
   *          the depth or, in other words, the position in the key we are
   *          currently working at
   * @return the node corresponding to the character of key at d
   */
  private TstNode put(TstNode parent, TstNode node, String key, V value, int d) {
    final char c = key.charAt(d);
    if ( node == null ) {
      node = new TstNode(parent, c);
    }
    if ( c < node.c ) {
      node.left = put(parent, node.left, key, value, d);
    } else if ( c > node.c ) {
      node.right = put(parent, node.right, key, value, d);
    } else if ( d < key.length() - 1 ) {
      node.middle = put(node, node.middle, key, value, d + 1);
    } else {
      node.value = ( value != null ) ? value : NULL;
    }
    return node;
  }

  @SuppressWarnings("unchecked")
  @Override
  public V get(String key) {
    if ( key == null ) { 
      return ( nullValue != NULL ) ? (V)nullValue : null;
    }
    if ( key.length() == 0 ) { 
      return ( emptyValue != NULL ) ? (V)emptyValue : null;
    }
    TstNode node = get(root, key, 0);
    return ( node != null ) && ( node.value != NULL ) ? (V)node.value : null;
  }

  /*
   * This implementation has a flaw which makes it not usable for production. It
   * does not distinct between a null value with a key being stored and the key
   * not being stored at all. This, however, is very commonly used in practice.
   * To fix this we just have to introduce a distinct null value (as is done
   * above) and use it in put and get.
   */
  private TstNode get(TstNode node, String key, int d) {
    if ( node == null ) {
      return null;
    }
    if ( d == key.length() ) {
      return node;
    }
    final char c = key.charAt(d);
    if ( c < node.c ) {
      return get(node.left, key, d);
    } else if ( c > node.c ) {
      return get(node.right, key, d);
    } else if ( d < key.length() - 1 ) {
      return get(node.middle, key, d + 1);
    }
    return node;
  }

  @Override
  public void delete(String key) {
    // TODO: test
    if ( key == null ) {
      nullValue = null;
      return;
    }
    if ( key.length() == 0 ) {
      emptyValue = null;
      return;
    }
    if ( delete(root, key, 0) ) {
      this.root = null;
    }
  }

  /**
   * @param node the current node
   * @param key the key to delete
   * @param d the depth in the key we are currently at
   * @return true, iff the node is empty after deletion
   */
  private boolean delete(TstNode node, String key, int d) {
    if (node == null) {
      return false;
    }
    if (d == key.length()) {
      node.value = null; // the actual deletion
    } else {
      final char c = key.charAt(d);
      if ( c < node.c ) {
        node.left = delete(node.left, key, d) ? null : node.left;
      } else if ( c > node.c ) {
        node.right = delete(node.right, key, d) ? null : node.right;
      } else {
        node.middle = delete(node.middle, key, d + 1) ? null : node.middle;
      }
    }
    return ( node.left == null && node.middle == null && node.right == null );
  }

  /*
   * This is not actually a useful implementation. The advantage of TernaryTries
   * over hash tables is that they often provide sublinear performance. But here
   * I put the keys into a hash set. This is just for laziness to provide a
   * proper iterator implementation.
   */
  @Override
  public Set<String> keySet() {
    // TODO: test
    Set<String> keys = new LinkedHashSet<>(); // linked for ordered
                                                    // iteration
    StringBuilder key = new StringBuilder();
    if ( nullValue != null ) { keys.add((String)(( nullValue == NULL ) ? null : nullValue)); }
    if ( emptyValue != null ) { keys.add(""); }
    collectInorder(root, key, keys);
    return Collections.unmodifiableSet(keys);
  }

  @Override
  public Set<String> keysWithPrefix(String aPrefix) {
    Set<String> keys = new LinkedHashSet<>();
    TstNode node = get(root, aPrefix, 0);
    if ( node != null ) {
      if ( node.value != null )
        keys.add(aPrefix);
      collectInorder(node.middle, new StringBuilder(aPrefix), keys);
    }
    return Collections.unmodifiableSet(keys);
  }

  private void collectInorder(TstNode node, StringBuilder prefix, Collection<String> keys) {
    if ( node == null ) {
      return;
    }
    if ( node.value != null )
      keys.add(new StringBuilder(prefix).append(node.c).toString());
    if ( node.left != null )
      collectInorder(node.left, prefix, keys);
    if ( node.middle != null )
      collectInorder(node.middle, new StringBuilder(prefix).append(node.c), keys);
    if ( node.right != null )
      collectInorder(node.right, prefix, keys);
  }

  @Override
  public boolean contains(String key) {
    // TODO: test
    if ( key == null )       { return nullValue != null; }
    if ( key.length() == 0 ) { return emptyValue != null; }
    TstNode node = get(root, key, 0);
    return ( node != null ) && ( node.value != null ); // deals with distinct null
  }

  @Override
  public String longestPrefix(String query) {
    // TODO: test
    int length = search(root, query, 0, 0);
    return query.substring(0, length);
  }

  private int search(TstNode x, String query, int d, int length) {
    // TODO: test
    if ( x == null )
      return length; // we are done in this subtree
    if ( x.value != null )
      length = d + 1; // update length
    if ( d == query.length() )
      return length; // we are done with the query
    char c = query.charAt(d);
    if ( c < x.c )
      return search(x.left, query, d, length);
    else if ( c > x.c )
      return search(x.right, query, d, length);
    else
      return search(x.middle, query, d + 1, length);
  }

  @Override
  public int rank(String aKey) {
    // TODO: test
    // TODO Auto-generated method stub
    return 0;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, V> range(String aKey1, String aKey2) {
    // TODO: test
    if (aKey1.compareTo(aKey2) > 0) {
      throw new IllegalArgumentException("aKey1 must be smaller or equal aKey2");
    }
    if ( aKey1.equals(aKey2) ) {
      TstNode node = this.get(root, aKey1, 0);
      if ( node == null ) {
        return Collections.emptyMap();
      }
      return Collections.singletonMap(aKey1, (node.value != null) ? (V)node.value : null);
    }
    final Map.Entry<String, V> floor = this.floor(aKey1);
    final Map.Entry<String, V> ceiling = this.ceiling(aKey1);
    
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteMin() {
    // TODO: test
    Map.Entry<String, V> entry = this.min();
    if ( entry == null ) {
      return;
    }
    this.delete(entry.getKey());
  }

  @Override
  public void deleteMax() {
    // TODO: test
    Map.Entry<String, V> entry = this.max();
    if ( entry == null ) {
      return;
    }
    this.delete(entry.getKey());
  }

  @Override
  public Map.Entry<String, V> max() {
    // TODO: test
    if ( isEmpty() ) {
      return null;
    }
    TstNode current = root;
    while (current.right != null) {
      current = current.right;
    }
    assert ( current != null && current.value != null );
    return new Entry<>(current);
  }

  @Override
  public Map.Entry<String, V> min() {
    // TODO: test
    if ( isEmpty() ) {
      return null;
    }
    TstNode current = root;
    while (current.left != null && current.value == null) {
      current = current.left;
    }
    assert ( current != null && current.value != null );
    return new Entry<>(current);
  }
  

  @Override
  public Map.Entry<String, V> floor(String key) {
    // TODO: test
    // TODO: consider NULLs first/last and key == null, require non-null?
    if ( key.length() == 0 ) { 
//      return toEntry(this.root);
    }
    TstNode node = get(root, key, 0);
    throw new RuntimeException("that implementation is not correct");
//    return ( node != null ) && ( node.value != NULL ) ? new Entry<V>(node) : null;
  }

  @Override
  public Map.Entry<String, V> ceiling(String key) {
    // TODO: test
    // TODO Auto-generated method stub
    return null;
  }
  
  private static class Entry<V> implements Map.Entry<String, V> {
    private final TstNode node;

    Entry(TstNode aNode) {
      node = aNode;
    }

    @Override
    public String getKey() {
      // TODO: test
      final StringBuilder key = new StringBuilder();
      for ( TstNode n = node; n != null; n = n.parent ) {
        key.append(n.c);
      }
      return key.reverse().toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getValue() {
      // TODO: test
      return (node.value != NULL) ? (V)node.value : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V setValue(V value) {
      // TODO: test
      Object prior = node.value; 
      node.value = (value != null) ? value : TernaryTree.NULL;      
      return (prior != NULL) ? (V)prior : null;
    }
  }
}
