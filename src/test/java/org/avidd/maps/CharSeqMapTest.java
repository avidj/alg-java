package org.avidd.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public abstract class CharSeqMapTest extends SymbolTableTest<StringSymbolTable<Integer>> {
  @Test
  public void testKeysWithPrefix() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    Collection<String> keySet = map.keysWithPrefix("fi");
    Assert.assertThat(keySet.size(), is(equalTo(3)));
    Assert.assertTrue(keySet.containsAll(Arrays.asList("fische", "fischt", "fischer's")));
  }

  @Test
  public void testLongestPrefix1() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fischereihafenarbeiter");
    Assert.assertThat(prefix, is(equalTo("fische")));
  }

  @Test
  public void testLongestPrefix2() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fischer'ssadfsadf");
    Assert.assertThat(prefix, is(equalTo("fischer's")));
  }

  @Test
  public void testLongestPrefix3() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fritzsfdsf");
    Assert.assertThat(prefix, is(equalTo("fritz")));
  }

  @Test
  public void testLongestPrefix4() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fritz");
    Assert.assertThat(prefix, is(equalTo("fritz")));
  }
  
  @Test @Ignore
  public void testRank() {
    Assert.fail();
  }
  
  @Test @Ignore
  public void testRange() {
    Assert.fail();
  }
  
  @Test @Ignore
  public void testDeleteMin() {
    Assert.fail();
  }

  @Test @Ignore
  public void testDeleteMax() {
    Assert.fail();
  }
}
