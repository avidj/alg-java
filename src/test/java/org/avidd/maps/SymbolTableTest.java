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
import org.junit.Assert;
import org.junit.Test;

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
    Assert.assertThat(map.keySet().size(), is(equalTo(50107)));
  }

  @Test
  public void testLeonardoDaVinci() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.leonardoDaVinci());
    Assert.assertThat(map.keySet().size(), is(equalTo(23589)));
  }

  @Test
  public void testOutlineOfScience() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.outlineOfScience());
    Assert.assertThat(map.keySet().size(), is(equalTo(18040)));
  }

  @Test
  public void testNull() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    Assert.assertThat(map.contains(NULL_KEY), is(false));
    map.put(NULL_KEY, null);
    Assert.assertThat(map.contains(NULL_KEY), is(true));
    Assert.assertThat(map.keySet().size(), is(equalTo(8)));
    Assert.assertThat(map.get(NULL_KEY), is(equalTo(null)));
  }

  @Test
  public void testNullKey() {
    SymbolTable<String, Integer> map = newSymbolTable(SEA_SHELLS);
    Assert.assertThat(map.contains(null), is(false));
    map.put(null, null);
    Assert.assertThat(map.contains(null), is(true));
    Assert.assertThat(map.keySet().size(), is(equalTo(8)));
    Assert.assertThat(map.get(null), is(equalTo(null)));
  }

  @Test
  public void testEmptyKeyAfter() {
    List<String> keys = SEA_SHELLS;
    SymbolTable<String, Integer> map = newSymbolTable(keys);
    Assert.assertThat(map.contains(EMPTY_STRING), is(false));
    map.put(EMPTY_STRING, 100);
    Assert.assertThat(map.contains(EMPTY_STRING), is(true));
    Collection<String> keySet = map.keySet();
    Assert.assertThat(keySet.size(), is(equalTo(8)));
    Assert.assertTrue(keySet.containsAll(keys));
    Assert.assertTrue(keySet.contains(""));
    Assert.assertThat(map.get(EMPTY_STRING), is(equalTo(100)));
    Assert.assertThat(map.get("s"), is(nullValue()));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      Assert.assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testEmptyKeyBefore() {
    SymbolTable<String, Integer> map = newSymbolTable();
    Assert.assertThat(map.contains(EMPTY_STRING), is(false));
    map.put(EMPTY_STRING, 100);
    Assert.assertThat(map.contains(EMPTY_STRING), is(true));
    List<String> keys = SEA_SHELLS;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    Assert.assertThat(map.keySet().size(), is(equalTo(8)));
    Assert.assertThat(map.get(EMPTY_STRING), is(equalTo(100)));
    Assert.assertThat(map.get("s"), is(nullValue()));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      Assert.assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testSeaShellsPut() {
    List<String> keys = SEA_SHELLS;
    SymbolTable<String, Integer> map = newSymbolTable(keys);
    Assert.assertThat(map.keySet().size(), is(equalTo(7)));
    Assert.assertThat(map.keySet(), is(equalTo((Set<String>)new HashSet<>(keys))));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      Assert.assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testFischersFritzPut() {
    List<String> keys = FISCHERS_FRITZ;
    SymbolTable<String, Integer> map = newSymbolTable(keys);
    Assert.assertThat(map.keySet().size(), is(equalTo(5)));
    Assert.assertThat(map.keySet(), is(equalTo((Set<String>)new HashSet<>(keys))));
    for ( int i = 0, n = keys.size(); i < n; i++ ) {
      Assert.assertThat(map.get(keys.get(i)), is(equalTo(keys.lastIndexOf(keys.get(i)))));
    }
  }

  @Test
  public void testFloor() {
    Assert.fail();
  }
  
  @Test
  public void testCeiling() {
    Assert.fail();
  }

  @Test
  public void testMin() {
    String[] sorted = UnsortedArrays.ulysses().clone();
    sorted = new LinkedHashSet<>(Arrays.asList(sorted)).toArray(new String[0]);
    Arrays.sort(sorted);
    sorted = Arrays.copyOfRange(sorted, 1000, 2000);
    SymbolTable<String, Integer> map = newSymbolTable(sorted);
    Assert.assertThat(map.min().getKey(), is(equalTo(sorted[0])));
  }

  @Test
  public void testMax() {
    SymbolTable<String, Integer> map = newSymbolTable(UnsortedArrays.ulysses());
    Assert.assertThat(map.max().getKey(), is(equalTo("")));
  }
  
  @Test
  public void testDelete() {
    Assert.fail();
  }
}
