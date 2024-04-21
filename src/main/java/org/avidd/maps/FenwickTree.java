package org.avidd.maps;

public class FenwickTree {

  int[] array; // 1-indexed array, In this array We save cumulative information
               // to perform efficient range queries and updates

  public FenwickTree(int size) {
    array = new int[size + 1];
  }

  /**
   * Range Sum query from 1 to ind ind is 1-indexed
   * <p/>
   * Time-Complexity: O(log(n))
   */
  public int rsq(int ind) {
    assert ind > 0;
    int sum = 0;
    while (ind > 0) {
      sum += array[ind];
      // Extracting the portion up to the first significant one of the binary
      // representation of 'ind' and decrementing ind by that number
      ind -= ind & (-ind);
    }

    return sum;
  }

  /**
   * Range Sum Query from a to b. Search for the sum from array index from a to
   * b a and b are 1-indexed
   * <p/>
   * Time-Complexity: O(log(n))
   */
  public int rsq(int a, int b) {
    assert b >= a && a > 0 && b > 0;

    return rsq(b) - rsq(a - 1);
  }

  /**
   * Update the array at ind and all the affected regions above ind. ind is
   * 1-indexed
   * <p/>
   * Time-Complexity: O(log(n))
   */
  public void update(int ind, int value) {
    assert ind > 0;
    while (ind < array.length) {
      array[ind] += value;
      // Extracting the portion up to the first significant one of the binary
      // representation of 'ind' and incrementing ind by that number
      ind += ind & (-ind);
    }
  }

  public int size() {
    return array.length - 1;
  }
}
