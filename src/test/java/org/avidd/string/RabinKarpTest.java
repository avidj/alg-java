package org.avidd.string;

public class RabinKarpTest extends StringSearchTest {

  @Override
  StringSearch stringSearch() {
    return new RabinKarpLasVegas();
  }
}
