package org.avidd.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.core.helpers.Assert;

import org.hamcrest.MatcherAssert;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public abstract class CharSeqMapTest extends SymbolTableTest<StringSymbolTable<Integer>> {
  @Test
  public void testKeysWithPrefix() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    Collection<String> keySet = map.keysWithPrefix("fi");
    MatcherAssert.assertThat(keySet.size(), is(equalTo(3)));
    assertTrue(keySet.containsAll(Arrays.asList("fische", "fischt", "fischer's")));
  }

  @Test
  public void testLongestPrefix1() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fischereihafenarbeiter");
    MatcherAssert.assertThat(prefix, is(equalTo("fische")));
  }

  @Test
  public void testLongestPrefix2() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fischer'ssadfsadf");
    MatcherAssert.assertThat(prefix, is(equalTo("fischer's")));
  }

  @Test
  public void testLongestPrefix3() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fritzsfdsf");
    MatcherAssert.assertThat(prefix, is(equalTo("fritz")));
  }

  @Test
  public void testLongestPrefix4() {
    StringSymbolTable<Integer> map = newSymbolTable();
    List<String> keys = FISCHERS_FRITZ;
    for ( int i = 0; i < keys.size(); i++ ) {
      map.put(keys.get(i), i);
    }
    String prefix = map.longestPrefix("fritz");
    MatcherAssert.assertThat(prefix, is(equalTo("fritz")));
  }
  
  @Test @Disabled
  public void testRank() {
    fail();
  }
  
  @Test @Disabled
  public void testRange() {
    fail();
  }
  
  @Test @Disabled
  public void testDeleteMin() {
    fail();
  }

  @Test @Disabled
  public void testDeleteMax() {
    fail();
  }
}
