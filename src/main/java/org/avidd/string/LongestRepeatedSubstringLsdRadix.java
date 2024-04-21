package org.avidd.string;

import org.avidd.sort.Sorts;

class LongestRepeatedSubstringLsdRadix extends LongestRepeatedSubstring {

  @Override
  protected void sort(String[] suffixes) {
    Sorts.lsdRadixSort(suffixes);
  }

}
