package org.avidd.string;

/**
 * KMP computes a deterministic finite automaton (DFA) from a pattern. It then
 * matches the pattern with an input String using that DFA in time linear to the
 * input text length.
 * 
 * TODO: change interface such that it can match against a stream, what would
 * search(..) return?
 */
class KnuthMorrisPratt implements StringSearch {

  @Override
  public int search(String pattern, String text) {
    final int[][] dfa = compile(pattern);
    // Reader in = new StringReader(pattern);//
    char[] chars = text.toCharArray();
    final int n = text.length();
    final int m = pattern.length();
    int i;
    int j;
    for ( i = 0, j = 0; i < n && j < m; i++ ) {
      j = dfa[chars[i]][j];
    }
    if ( j == m )
      return i - m;
    return -1;
  }

  private static final int[][] compile(String pattern) {
    final int radix = 256;
    final int m = pattern.length();
    final int[][] dfa = new int[radix][pattern.length()];
    dfa[pattern.charAt(0)][0] = 1; // no matches, and next char is first of
                                   // pattern
    for ( int i = 0, j = 1; j < m; j++ ) { // we are in i if we had a mismatch
                                           // for j - 1, else j
      for ( int c = 0; c < radix; c++ )
        // copy mismatch cases for _each_ possible char
        dfa[c][j] = dfa[c][i]; // ???
      dfa[pattern.charAt(j)][j] = j + 1; // set match case
      i = dfa[pattern.charAt(j)][i]; // update restart state
    }
    return dfa;
  }

}
