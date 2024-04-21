package org.avidd.string;

public class BruteForceTest extends StringSearchTest {

  @Override
  StringSearch stringSearch() {
    return new BruteForce();
  }
}
