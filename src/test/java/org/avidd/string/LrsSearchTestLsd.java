package org.avidd.string;

public class LrsSearchTestLsd extends LongestRepeatedSubstringSearchTest {

  public LongestRepeatedSubstring stringSearch() {
    return new LongestRepeatedSubstringLsdRadix();
  }
}
