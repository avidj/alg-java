package org.avidd.sort;

import java.util.Random;
import java.io.File;
import java.io.PrintWriter;

public class RandomArrayGenerator {
  private static final Random rand = new Random();

  public static void main(String[] args) {
    //int[] numbers = genNumbers(10);
    // writeNumbers(numbers);
    // numbers = genNumbers(100);
    // writeNumbers(numbers);
    // numbers = genNumbers(1000);
    // writeNumbers(numbers);
    // numbers = genNumbers(10000);
    // writeNumbers(numbers);
    // numbers = genNumbers(50000);
    // writeNumbers(numbers);
    //numbers = genNumbers(500000);
    // numbers = genNumbers(10000000);
    int[] numbers = genNumbers(1000);
    writeNumbers(numbers);
    numbers = genNumbers(10000);
    writeNumbers(numbers);
    numbers = genNumbers(100000);
    writeNumbers(numbers);
    numbers = genNumbers(1000000);
    writeNumbers(numbers);
    numbers = genNumbers(10000000);
    writeNumbers(numbers);
    numbers = genNumbers(100000000);
    writeNumbers(numbers);
  }

  private static int[] genNumbers(int count) {
    int[] numbers = new int[count];
    for ( int i = 0; i < count; i++ ) {
      numbers[i] = rand.nextInt();
    }
    return numbers;
  }

  private static void writeNumbers(int[] numbers) {
    File file = new File(numbers.length + ".txt");
    try ( PrintWriter out = new PrintWriter(file) ) {
      out.println(numbers.length);
      int current;
      for ( int i = 0; i < numbers.length; i++ ) {
        current = numbers[i] % ( numbers.length * 4 );
        if ( current < 0 )
          current *= -1;
        out.println(current);
      }
      out.close();
    } catch ( Exception e ) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }
}
