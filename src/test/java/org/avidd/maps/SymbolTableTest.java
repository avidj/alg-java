package org.avidd.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.avidd.sort.UnsortedArrays;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    assertCorpusKeyCount(UnsortedArrays.ulysses());
  }

  @Test
  public void testLeonardoDaVinci() {
    assertCorpusKeyCount(UnsortedArrays.leonardoDaVinci());
  }

  @Test
  public void testOutlineOfScience() {
    assertCorpusKeyCount(UnsortedArrays.outlineOfScience());
  }

  /** The table must hold exactly the distinct tokens of the corpus, differentially checked
   * against {@link HashSet}. */
  private void assertCorpusKeyCount(String[] corpus) {
    SymbolTable<String, Integer> map = newSymbolTable(corpus);
    Set<String> distinct = new HashSet<>(Arrays.asList(corpus));
    MatcherAssert.assertThat(map.keySet().size(), is(equalTo(distinct.size())));
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

  /** Probes around, between, on and outside the SEA_SHELLS keys. */
  private static final List<String> FLOOR_CEILING_PROBES = Arrays.asList(
      "a", "by", "by ", "c", "sea", "sf", "sh", "she", "shellz", "shorf", "the", "thf", "z");

  @Test
  public void testFloor() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    TreeSet<String> model = new TreeSet<>(SEA_SHELLS);
    for ( String probe : FLOOR_CEILING_PROBES ) {
      Map.Entry<String, Integer> floor = map.floor(probe);
      assertThat("floor(\"" + probe + "\")", floor == null ? null : floor.getKey(),
          is(equalTo(model.floor(probe))));
    }
  }

  @Test
  public void testCeiling() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    TreeSet<String> model = new TreeSet<>(SEA_SHELLS);
    for ( String probe : FLOOR_CEILING_PROBES ) {
      Map.Entry<String, Integer> ceiling = map.ceiling(probe);
      assertThat("ceiling(\"" + probe + "\")", ceiling == null ? null : ceiling.getKey(),
          is(equalTo(model.ceiling(probe))));
    }
  }

  @Test
  public void testMin() {
    String[] sorted = distinctSortedUlyssesSlice();
    SymbolTable<String, Integer> map = newSymbolTable(sorted);
    assertThat(map.min().getKey(), is(equalTo(sorted[0])));
  }

  @Test
  public void testMax() {
    String[] sorted = distinctSortedUlyssesSlice();
    SymbolTable<String, Integer> map = newSymbolTable(sorted);
    assertThat(map.max().getKey(), is(equalTo(sorted[sorted.length - 1])));
  }

  private static String[] distinctSortedUlyssesSlice() {
    String[] sorted = new LinkedHashSet<>(Arrays.asList(UnsortedArrays.ulysses()))
        .toArray(new String[0]);
    Arrays.sort(sorted);
    return Arrays.copyOfRange(sorted, 1000, 2000);
  }

  @Test
  public void testDelete() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    TreeSet<String> model = new TreeSet<>(SEA_SHELLS);

    for ( String key : Arrays.asList("she", "by", "zzz" /* not present */) ) {
      map.delete(key);
      model.remove(key);
      assertThat("contains(\"" + key + "\") after delete", map.contains(key), is(false));
      assertThat(map.keySet(), is(equalTo((Set<String>)model)));
    }
    // the surviving keys must keep their values
    for ( String key : model ) {
      assertThat(map.get(key), is(equalTo(SEA_SHELLS.lastIndexOf(key))));
    }
    // deleting the rest must empty the table
    for ( String key : new TreeSet<>(model) ) {
      map.delete(key);
      model.remove(key);
      assertThat(map.keySet(), is(equalTo((Set<String>)model)));
    }
    assertThat(map.keySet().size(), is(equalTo(0)));
  }
}
