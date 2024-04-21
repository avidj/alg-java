package org.avid.diag;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public final class ContinuousDiagonalTest {

	@Test
	public void testTrivial() {
		char[][] matrix = new char[][] {};
		String string = ContinuousDiagonal.continuousDiagonal(matrix);
		assertThat(string, is(equalTo("")));
	}

	@Test
	public void testMinimal() {
		char[][] matrix = new char[][] { { 'e' } };
		String string = ContinuousDiagonal.continuousDiagonal(matrix);
		assertThat(string, is(equalTo("e")));
	}

	@Test
	public void testQuadratic4() {
		char[][] matrix = new char[][] {
			{ 'e', '4', 't', '6' },
			{ '!', '1', '2', '3' },
			{ 'E', 'F', '0', '7' },
			{ 'o', 'j', 'e', 'ü' }
		};
		String string = ContinuousDiagonal.continuousDiagonal(matrix);
		assertThat(string, is(equalTo("e!4E1toF26j03e7ü")));
	}
}
