package org.avid.diag;

public final class ContinuousDiagonal {

	public static String continuousDiagonal(char[][] matrix) {
		StringBuilder string = new StringBuilder();
		for ( int sum = 0; sum < matrix.length; sum++ ) {
			for (int i = 0; i <= sum; i++) {
				string.append(matrix[sum - i][i]);
			}
		}
		for ( int sum = matrix.length; sum <= (matrix.length - 1) * 2; sum++ ) {
			for (int i = sum - matrix.length + 1; i < matrix.length; i++) {
				string.append(matrix[sum - i][i]);
			}
		}
		return string.toString();
	}
}
