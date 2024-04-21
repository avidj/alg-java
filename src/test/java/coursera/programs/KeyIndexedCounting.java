package coursera.programs;

public class KeyIndexedCounting {

  public static void main(String[] args) {
    System.out.println(String.copyValueOf(sort("abracadabra".toCharArray())));
  }

  static char[] sort(char[] a) {
    final int radix = 65536;
    int n = a.length;
    char[] aux = new char[a.length];
    int[] count = new int[radix + 1]; // mind the offset!

    // increment the count for the character _after_ that at position i
    for ( int i = 0; i < n; i++ ) {
      System.out.println(String.format("a[%1$d] = %2$s = %3$d", i, a[i], (int)a[i]));
      count[a[i] + 1]++; // if a[i] is 'a' then there is one more character <
                         // (char)(a[i] + 1)
    }

    // accumulate counts, now count[r] is the number of keys smaller or equal r
    for ( int r = 0; r < radix; r++ )
      count[r + 1] += count[r];

    System.out.println("accumulated counts:");
    for ( int i = 0; i < count.length; i++ )
      if ( i == 0 || count[i - 1] != count[i] )
        System.out.println(String.format("count[%1$d] = %2$d", i, count[i]));

    for ( int i = 0; i < n; i++ ) {
      System.out.println(String.format("a[%1$d] = %2$c, count[a[%1$d] = %2$c = %3$d] = %4$d", i,
          (char)a[i], (int)a[i], count[a[i]]));
      aux[count[a[i]]++] = a[i];
    }

    // just copy from the auxiliary to the original array
    for ( int i = 0; i < n; i++ )
      a[i] = aux[i];

    return aux;
  }

}
