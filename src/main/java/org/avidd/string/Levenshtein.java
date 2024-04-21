package org.avidd.string;

public class Levenshtein implements EditDistance {

	@Override
	public int editDistance(String x, String y) {
		int n = x.length();
		int m = y.length();
		int[][] d = new int[n + 1][m + 1];

		for ( int i = 0; i <= n; i++ ) {
			d[i][0] = i;
		}
		for ( int j = 0; j <= m; j++ ) {
			d[0][j] = j;
		}

	    for (int i = 1; i <= n; i++) {
	        for (int j = 1; j <= m; j++) {
               d[i][j] = Math.min(d[i - 1][j - 1] + edit(x, i, y, j), 
	                       Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1));
	        }
	    }

	    return d[x.length()][y.length()];
	}
	
	private int edit(String s1, int i, String s2, int j) {
		return s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
	}
}
