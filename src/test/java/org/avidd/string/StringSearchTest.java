package org.avidd.string;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public abstract class StringSearchTest {
  private static final String LONG_TEXT = "Now is the time for all people to come to the aid of their party. Now is the time for all good people tocome to the aid of their party. Now is the time for many good people to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for a lot of good people to come to the aid of their party. Now is the time for all of the good people to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for each good person to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for all good Republicans to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for many or all good people to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for all good Democrats to come to the aid of their party. Now is the time for all people to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for many good people to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for a lot of good people to come to the aid of their party. Now is the time for all of the good people to come to the aid of their party. Now is the time for all good people to come to the aid of their attack at dawn party. Now is the time for each person to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for all good Republicans to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for many or all good people to come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is the time for all good Democrats to come to the aid of their party.";

  abstract StringSearch stringSearch();

  @Test
  public void testNeedleInAHaystack() {
    final String text = "HAYSTACKNEEDLEINAHAYSTACK";
    final String pattern = "NEEDLE";
    final int pos = stringSearch().search(pattern, text);
    MatcherAssert.assertThat(pos, is(equalTo(8)));
  }

  @Test
  public void testPrefix() {
    final String text = "PrefixHAYSTACKNEEDLEINAHAYSTACK";
    final String pattern = "Prefix";
    final int pos = stringSearch().search(pattern, text);
    MatcherAssert.assertThat(pos, is(equalTo(0)));
  }

  @Test
  public void testSuffix() {
    final String text = "HAYSTACKNEEDLEINAHAYSTACKSuffix";
    final String pattern = "Suffix";
    final int pos = stringSearch().search(pattern, text);
    MatcherAssert.assertThat(pos, is(equalTo(text.length() - pattern.length())));
  }

  @Test
  public void testLongTextLate() {
    final String pattern = "all good Democrats";
    final int pos = stringSearch().search(pattern, LONG_TEXT);
    MatcherAssert.assertThat(pos, is(equalTo(964)));
  }
}
