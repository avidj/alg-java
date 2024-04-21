package org.avidd.string;

public class KnuthMorrisPrattTest extends StringSearchTest {

  @Override
  StringSearch stringSearch() {
    return new KnuthMorrisPratt();
  }
}
