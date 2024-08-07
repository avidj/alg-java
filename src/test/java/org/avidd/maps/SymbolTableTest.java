package org.avidd.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.avidd.sort.UnsortedArrays;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public abstract class SymbolTableTest<T extends SymbolTable<String, Integer>> {
  static final List<String> SEA_SHELLS = Arrays.asList("she", "sells", "sea", "shells",
      "by", "the", "shore");
  static final List<String> FISCHERS_FRITZ = Arrays.asList("fischer's", "fritz", "fischt",
      "frische", "fische", "frische", "fische", "fischt", "fischer's", "fritz");
  static final String NULL_KEY = null;
  static final String EMPTY_STRING = "";

  /** @return the symbol table instance to test */
  abstract T newSymbolTable();
  
  T newSymbolTable(String[] keys) {
    return newSymbolTable(Arrays.asList(keys));
  }

  T newSymbolTable(List<String> keys) {
    T map = newSymbolTable();
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    return map;
  }

  @Test
  public void testUlysses() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.ulysses());
    MatcherAssert.assertThat(map.keySet().size(), is(equalTo(50107)));
  }

  @Test
  public void testLeonardoDaVinci() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.leonardoDaVinci());
    MatcherAssert.assertThat(map.keySet().size(), is(equalTo(23589)));
  }

  @Test
  public void testOutlineOfScience() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.outlineOfScience());
    MatcherAssert.assertThat(map.keySet().size(), is(equalTo(18040)));
  }

  @Test
  public void testNull() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    MatcherAssert.assertThat(map.contains(NULL_KEY), is(false));
    map.put(NULL_KEY, null);
    MatcherAssert.assertThat(map.contains(NULL_KEY), is(true));
    MatcherAssert.assertThat(map.keySet().size(), is(equalTo(8)));
    MatcherAssert.assertThat(map.get(NULL_KEY), is(equalTo(null)));
  }

  @Test
  public void testNullKey() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    MatcherAssert.assertThat(map.contains(null), is(false));
    map.put(null, null);
    MatcherAssert.assertThat(map.contains(null), is(true));
    MatcherAssert.assertThat(map.keySet().size(), is(equalTo(8)));
    MatcherAssert.assertThat(map.get(null), is(equalTo(null)));
  }

  @Test
  public void testEmptyKeyAfter() {
    List<String> keys = SEA_SHELLS;
    SymbolTable<String, Integer> map = newSymbolTable(keys);
    assertThat(map.contains(EMPTY_STRING), is(false));
    map.put(EMPTY_STRING, 100);
    assertThat(map.contains(EMPTY_STRING), is(true));
    Collection<String> keySet = map.keySet();
    assertThat(keySet.size(), is(equalTo(8)));
    assertTrue(keySet.containsAll(keys));
    assertTrue(keySet.contains(""));
    assertThat(map.get(EMPTY_STRING), is(equalTo(100)));
    assertThat(map.get("s"), is(nullValue()));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testEmptyKeyBefore() {
    SymbolTable<String, Integer> map = newSymbolTable();
    assertThat(map.contains(EMPTY_STRING), is(false));
    map.put(EMPTY_STRING, 100);
    assertThat(map.contains(EMPTY_STRING), is(true));
    List<String> keys = SEA_SHELLS;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    assertThat(map.keySet().size(), is(equalTo(8)));
    assertThat(map.get(EMPTY_STRING), is(equalTo(100)));
    assertThat(map.get("s"), is(nullValue()));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testSeaShellsPut() {
    List<String> keys = SEA_SHELLS;
    SymbolTable<String, Integer> map = newSymbolTable(keys);
    assertThat(map.keySet().size(), is(equalTo(7)));
    assertThat(map.keySet(), is(equalTo((Set<String>)new HashSet<>(keys))));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testFischersFritzPut() {
    List<String> keys = FISCHERS_FRITZ;
    SymbolTable<String, Integer> map = newSymbolTable(keys);
    assertThat(map.keySet().size(), is(equalTo(5)));
    assertThat(map.keySet(), is(equalTo((Set<String>)new HashSet<>(keys))));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testFloor() {
    fail();
  }
  
  @Test
  public void testCeiling() {
    fail();
  }

  @Test
  public void testMin() {
    String[] sorted = UnsortedArrays.ulysses().clone();
    sorted = new LinkedHashSet<>(Arrays.asList(sorted)).toArray(new String[0]);
    Arrays.sort(sorted);
    sorted = Arrays.copyOfRange(sorted, 1000, 2000);
    SymbolTable<String, Integer> map = newSymbolTable(sorted);
    assertThat(map.min().getKey(), is(equalTo(sorted[0])));
  }

  @Test
  public void testMax() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.ulysses());
    assertThat(map.max().getKey(), is(equalTo("")));
  }
  
  @Test
  public void testDelete() {
    fail();
  }
}
