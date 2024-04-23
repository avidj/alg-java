package org.avidd.maps;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Left-leaning red black BSTs are perfectly balanced symbol tables allowing for
 * ordered iteration. They are a simulation of 2-3 trees which have the same
 * properties but are harder to implement.
 * 
 * @author David Kensche
 * 
 * @param <K>
 *          key type
 * @param <V>
 *          value type
 */
public class LLRedBlackBst<K, V> implements SymbolTable<K, V> {
  private static final boolean RED = true;
  private static final boolean BLACK = false;

  private boolean containsNull = false;
  private V nullValue;
  private final Comparator<K> comparator;
  private Node<K, V> root;

  public LLRedBlackBst(Comparator<K> aComparator) {
    comparator = aComparator;
  }

  @Override
  public void put(K key, V val) {
    if ( key == null ) {
      containsNull = true;
      nullValue = val;
      return;
    }
    root = put(root, key, val);
    root.color = BLACK;
  }

  private Node<K, V> put(Node<K, V> h, K key, V val) {
    if ( h == null )
      return new Node<>(key, val, RED);
    int cmp = comparator.compare(key, h.key);

    // update left, right, or value
    if ( cmp < 0 )
      h.left = put(h.left, key, val);
    else if ( cmp > 0 )
      h.right = put(h.right, key, val);
    else
      h.val = val;

    // rebalance
    if ( isRed(h.right) && !isRed(h.left) )
      h = rotateLeft(h);
    if ( isRed(h.left) && isRed(h.left.left) )
      h = rotateRight(h);
    if ( isRed(h.left) && isRed(h.right) )
      flipColors(h);

    return h;
  }

  @Override
  public V get(K key) {
    if ( key == null ) {
      return nullValue;
    }
    Node<K, V> x = root;
    while ( x != null ) {
      int cmp = comparator.compare(key, x.key);
      if ( cmp < 0 )
        x = x.left;
      else if ( cmp > 0 )
        x = x.right;
      else
        return x.val;
    }
    return null;
  }

  private V get(Node<K, V> x, K key) {
    while ( x != null ) {
      int cmp = comparator.compare(key, x.key);
      if ( cmp < 0 )
        return get(x.left, key);
      if ( cmp > 0 )
        return get(x.right, key);
      else
        return x.val;
    }
    return null;
  }

  public void deleteMin() {
    root = deleteMin(root);
    root.color = BLACK;
  }

  private Node<K, V> deleteMin(Node<K, V> h) {
    if ( h.left == null )
      return null;
    if ( !isRed(h.left) && !isRed(h.left.left) )
      h = moveRedLeft(h);
    h.left = deleteMin(h.left);
    return fixUp(h);
  }

  private Node<K, V> fixUp(Node<K, V> h) {
    // TODO Auto-generated method stub
    return null;
  }

  private Node<K, V> moveRedLeft(Node<K, V> h) {
    flipColors(h);
    if ( isRed(h.right.left) ) {
      h.right = rotateRight(h.right);
      h = rotateLeft(h);
      flipColors(h);
    }
    return h;
  }

  private Node<K, V> moveRedRight(Node<K, V> h) {
    flipColors(h);
    if ( isRed(h.left.left) ) {
      h = rotateRight(h);
      flipColors(h);
    }
    return h;
  }

  @Override
  public void delete(K key) {
    if ( key == null ) {
      containsNull = false;
      nullValue = null;
      return;
    }
    root = delete(root, key);
    root.color = BLACK;
  }

  private Node<K, V> delete(Node<K, V> h, K key) {
    // see http://www.cs.princeton.edu/~rs/talks/LLRB/LLRB.pdf
    if ( comparator.compare(key, h.key) < 0 ) {
      if ( !isRed(h.left) && !isRed(h.left.left) )
        h = moveRedLeft(h);
      h.left = delete(h.left, key);

    } else {
      if ( isRed(h.left) )
        h = rotateRight(h);
      if ( comparator.compare(key, h.key) == 0 && ( h.right == null ) )
        return null;
      if ( !isRed(h.right) && !isRed(h.right.left) )
        h = moveRedRight(h);
      if ( comparator.compare(key, h.key) == 0 ) {
        h.val = get(h.right, min(h.right).getKey());
        h.key = min(h.right).getKey();
        h.right = deleteMin(h.right);
      } else
        h.right = delete(h.right, key);
    }
    return fixUp(h);
  }

  @Override
  public Set<K> keySet() {
    Set<K> keys = new LinkedHashSet<>();
    collectKeys(root, keys);
    if ( containsNull ) {
      keys.add(null);
    }
    return keys;
  }

  // TODO: not tested
  private void collectKeys(Node<K, V> h, Set<K> keys) {
    if ( h.left != null )
      collectKeys(h.left, keys);
    keys.add(h.key);
    if ( h.right != null )
      collectKeys(h.right, keys);
  }

  @Override
  public boolean contains(K key) {
    return ( get(key) != null || ( key == null && containsNull ) );
  }

  // rotate a temporarily right-leaning red link to a left-leaning one
  private Node<K, V> rotateLeft(Node<K, V> h) {
    assert ( isRed(h.right) );
    Node<K, V> x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;
    return x;
  }

  // there are two subsequent left red leaning links
  private Node<K, V> rotateRight(Node<K, V> h) {
    assert ( isRed(h.left) );
    Node<K, V> x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    return x;
  }

  private void flipColors(Node<K, V> h) {
    // assert ( !isRed(h) );
    // assert ( isRed(h.left) );
    // assert ( isRed(h.right) );
    // h.left.color = BLACK;
    // h.right.color = BLACK;
    // h.color = RED;
    h.left.color = !h.left.color;
    h.right.color = !h.right.color;
    h.color = !h.color;
  }

  private static boolean isRed(Node<?, ?> x) {
    return x != null && x.color;
  }

  @Override
  public Map.Entry<K, V> floor(K key) {
    return floor(root, key);
  }

  private Map.Entry<K, V> floor(Node<K, V> h, K key) {
    if ( h == null ) return null;
    int cmp = comparator.compare(key, h.key);    
    if      ( cmp < 0 ) return floor(h.left, key);
    else if ( cmp > 0 ) {
      Map.Entry<K, V> f = floor(h.right, key);
      if ( f != null ) return f;       // if there is a key in right subtree it is the floor
    }
    return new ImmutableNode<>(h); // k == key at root or there is no key in right subtree
  }

  @Override
  public Map.Entry<K, V> ceiling(K key) {
    return ceil(root, key);
  }
  
  private Map.Entry<K, V> ceil(Node<K, V> h, K key) {
    if ( h == null ) return null;
    int cmp = comparator.compare(key, h.key);
    if ( cmp > 0 ) return ceil(h.right, key);
    else if ( cmp < 0 ) {
      Map.Entry<K, V> c = ceil(h.left, key);
      if ( c != null ) return c;
    }
    return new ImmutableNode<>(h);
  }

  @Override
  public Map.Entry<K, V> max() {
    return max(root);
  }
  
  private Map.Entry<K, V> max(Node<K, V> h) {
    if ( h == null ) return null;
    if ( h.right == null ) return new ImmutableNode<>(h);
    return max(h.right);
  }

  @Override
  public Map.Entry<K, V> min() {
    return min(root);
  }
  
  private Map.Entry<K, V> min(Node<K, V> h) {
    if ( h == null ) return null;
    if ( h.left == null ) return new ImmutableNode<>(h);
    return min(h.left);
  }

  private static class ImmutableNode<K, T> implements Map.Entry<K, T> {
    private final Node<K, T> node;

    ImmutableNode(Node<K, T> aNode) {
      node = aNode;
    }

    @Override
    public K getKey() {
      return node.key;
    }

    @Override
    public T getValue() {
      return node.val;
    }

    @Override
    public T setValue(T value) {
      throw new UnsupportedOperationException();
    }
  }

  private static class Node<K, T> {
    private K key;
    private T val;
    private Node<K, T> left;
    private Node<K, T> right;
    private boolean color = RED;

    Node(K aKey, T aVal, boolean aColor) {
      key = aKey;
      val = aVal;
      color = aColor;
    }

    @Override
    public String toString() {
      StringBuilder string = new StringBuilder("Node(");
      if ( isRed(this) ) {
        string.append("RED");
      } else {
        string.append("BLACK");
      }
      return string.append(", ").append(key).append(")").toString();
    }
  }
}
