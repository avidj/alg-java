package org.avidd.string;

public class RabinKarpIncrementalTest extends StringSearchTest {

  @Override
  StringSearch stringSearch() {
    return new RabinKarpLasVegasIncremental();
  }
}
