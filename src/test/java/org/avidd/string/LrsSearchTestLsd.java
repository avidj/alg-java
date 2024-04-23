package org.avidd.string;

public class LrsSearchTestLsd extends LongestRepeatedSubstringSearchTest {

  @Override
  public LongestRepeatedSubstring stringSearch() {
    return new LongestRepeatedSubstringLsdRadix();
  }
}
