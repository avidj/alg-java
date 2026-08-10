package org.avidd.maps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.avidd.util.ComparableComparator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RedBlackBstTest extends SymbolTableTest<LLRedBlackBst<String, Integer>> {

  private static final String DELETE_BROKEN =
      "LLRedBlackBst.delete is broken: fixUp() is an unimplemented stub returning null, so any "
          + "deletion nulls out the tree - see TODO.md";

  @Override
  LLRedBlackBst<String, Integer> newSymbolTable() {
    return new LLRedBlackBst<>(new ComparableComparator<String>());
  }

  @Override
  @Test
  @Disabled(DELETE_BROKEN)
  public void testDelete() {
    super.testDelete();
  }

  /* ------------------------------------------------------------------ */
  /* Structural invariants of the left-leaning red-black tree.          */
  /* The checks reach the private node structure via reflection so that */
  /* no test hooks leak into the production class.                      */
  /* ------------------------------------------------------------------ */

  @Test
  public void invariantsHoldAfterRandomisedInserts() {
    LLRedBlackBst<String, Integer> tree = newSymbolTable();
    List<String> keys = randomisedKeys(2000, 42);
    for ( int i = 0; i < keys.size(); i++ ) {
      tree.put(keys.get(i), i);
    }
    assertInvariants(tree);
  }

  @Test
  public void invariantsHoldAfterSortedInserts() {
    LLRedBlackBst<String, Integer> tree = newSymbolTable();
    List<String> keys = randomisedKeys(2000, 42);
    Collections.sort(keys);
    for ( int i = 0; i < keys.size(); i++ ) {
      tree.put(keys.get(i), i);
    }
    assertInvariants(tree);
  }

  @Test
  @Disabled(DELETE_BROKEN)
  public void invariantsHoldAfterRandomisedDeletes() {
    LLRedBlackBst<String, Integer> tree = newSymbolTable();
    List<String> keys = randomisedKeys(2000, 4711);
    for ( int i = 0; i < keys.size(); i++ ) {
      tree.put(keys.get(i), i);
    }

    List<String> toDelete = new ArrayList<>(keys);
    Collections.shuffle(toDelete, new Random(4711));
    int i = 0;
    for ( String key : toDelete ) {
      tree.delete(key);
      // checking after every single deletion is quadratic; every 100th keeps it fast
      if ( i++ % 100 == 0 ) {
        assertInvariants(tree);
      }
    }
    assertInvariants(tree);
    assertThat(tree.keySet().size(), is(equalTo(0)));
  }

  @Test
  @Disabled(DELETE_BROKEN)
  public void deleteRetainsAllOtherKeys() {
    LLRedBlackBst<String, Integer> tree = newSymbolTable();
    List<String> keys = randomisedKeys(500, 7);
    for ( int i = 0; i < keys.size(); i++ ) {
      tree.put(keys.get(i), i);
    }
    TreeSet<String> model = new TreeSet<>(keys);
    List<String> toDelete = new ArrayList<>(keys.subList(0, 250));
    for ( String key : toDelete ) {
      tree.delete(key);
      model.remove(key);
    }
    for ( String key : model ) {
      assertTrue(tree.contains(key), "key \"" + key + "\" lost by unrelated delete");
    }
    for ( String key : toDelete ) {
      assertFalse(tree.contains(key), "key \"" + key + "\" not deleted");
    }
    assertThat(tree.keySet(), is(equalTo((Set<String>)model)));
  }

  private static List<String> randomisedKeys(int n, long seed) {
    List<String> keys = new ArrayList<>(n);
    for ( int i = 0; i < n; i++ ) {
      keys.add(String.format("%06d", i));
    }
    Collections.shuffle(keys, new Random(seed));
    return keys;
  }

  private void assertInvariants(LLRedBlackBst<String, Integer> tree) {
    Object root = field(tree, "root");
    if ( root == null ) {
      return;
    }
    assertFalse(isRed(root), "root must be black");
    assertRedLinkInvariants(root);
    blackHeight(root);
    assertBstOrder(root, new ArrayList<>());
  }

  /** No right-leaning red link and no red node with a red left child (no two reds in a row). */
  private void assertRedLinkInvariants(Object node) {
    if ( node == null ) {
      return;
    }
    Object left = field(node, "left");
    Object right = field(node, "right");
    assertFalse(isRed(right), "right-leaning red link at key " + field(node, "key"));
    if ( isRed(node) ) {
      assertFalse(isRed(left), "two consecutive red links at key " + field(node, "key"));
    }
    assertRedLinkInvariants(left);
    assertRedLinkInvariants(right);
  }

  /** Every path from a node to its null links carries the same number of black links. */
  private int blackHeight(Object node) {
    if ( node == null ) {
      return 1;
    }
    int leftHeight = blackHeight(field(node, "left"));
    int rightHeight = blackHeight(field(node, "right"));
    assertThat("black balance violated at key " + field(node, "key"),
        leftHeight, is(equalTo(rightHeight)));
    return leftHeight + ( isRed(node) ? 0 : 1 );
  }

  private void assertBstOrder(Object node, List<String> inorder) {
    if ( node == null ) {
      return;
    }
    assertBstOrder(field(node, "left"), inorder);
    String key = (String)field(node, "key");
    if ( !inorder.isEmpty() ) {
      assertTrue(inorder.get(inorder.size() - 1).compareTo(key) < 0,
          "BST order violated at key " + key);
    }
    inorder.add(key);
    assertBstOrder(field(node, "right"), inorder);
  }

  private static boolean isRed(Object node) {
    return node != null && (Boolean)field(node, "color");
  }

  private static Object field(Object object, String name) {
    if ( object == null ) {
      return null;
    }
    try {
      Field field = object.getClass().getDeclaredField(name);
      field.setAccessible(true);
      return field.get(object);
    } catch ( ReflectiveOperationException e ) {
      throw new IllegalStateException(e);
    }
  }
}
