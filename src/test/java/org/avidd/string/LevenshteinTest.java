package org.avidd.string;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class LevenshteinTest {
  private final EditDistance ed = new Levenshtein();

  @Test
  public void testEqualStrings() {
    final String s1 = "Potter";
    final String s2 = s1;
    MatcherAssert.assertThat(ed.editDistance(s1, s2), is(equalTo(0)));
  }

  @Test
  public void testAdd1() {
    final String s1 = "Poter";
    final String s2 = "Potter";
    MatcherAssert.assertThat(ed.editDistance(s1, s2), is(equalTo(1)));
  }

  @Test
  public void testDelete1() {
    final String s1 = "Potter";
    final String s2 = "Poter";
    MatcherAssert.assertThat(ed.editDistance(s1, s2), is(equalTo(1)));
  }

  @Test
  public void testReal() {
    final String s1 = "A_SalesOrderWithoutCharge";
    final String s2 = "A_SlsOrdWthoutChrg";
    MatcherAssert.assertThat(ed.editDistance(s1, s2), is(equalTo(7)));
  }
}
