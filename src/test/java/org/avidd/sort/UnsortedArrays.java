package org.avidd.sort;

import static org.avidd.util.FileUtil.readStrings;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.avidd.util.ObjectPool;

public final class UnsortedArrays {
  private static final Random RANDOM = new Random(System.currentTimeMillis());
  private static final List<String> ULYSSES = readStrings("target/test-classes/pg4300.txt");
  private static final List<String> LEONARDO_DA_VINCI = readStrings("target/test-classes/pg5000.txt");
  private static final List<String> OUTLINE_OF_SCIENCE = readStrings("target/test-classes/pg20417.txt");
  private static final int[] UNSORTED_1K = genNumbers(1000);
  private static final int[] UNSORTED_10K = genNumbers(10000);
  private static final int[] UNSORTED_100K = genNumbers(100000);
  private static final int[] UNSORTED_1M = genNumbers(1000000);
  private static final int[] UNSORTED_10M = genNumbers(10000000);

  private static final Comparator<Integer> NATURAL_ORDER = new Comparator<>() {
    @Override
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
  };

  private UnsortedArrays() { /* hidden utility class constructor  */}

  public static Comparator<Integer> naturalOrder() {
    return NATURAL_ORDER;
  }  

  public static String[] ulysses() {
    return ULYSSES.toArray(new String[ULYSSES.size()]);
  }

  public static String[] leonardoDaVinci() {
    return LEONARDO_DA_VINCI.toArray(new String[LEONARDO_DA_VINCI.size()]);
  }

  public static String[] outlineOfScience() {
    return OUTLINE_OF_SCIENCE.toArray(new String[OUTLINE_OF_SCIENCE.size()]);
  }

  public static int[] unsorted1K() {
    return copyOfInt(UNSORTED_1K);
  }

  public static int[] unsorted10K() {
    return copyOfInt(UNSORTED_10K);
  }

  public static int[] unsorted100K() {
    return copyOfInt(UNSORTED_100K);
  }

  public static int[] unsorted1M() {
    return copyOfInt(UNSORTED_1M);
  }

  public static Integer[] unsorted1KInteger() {
    return copyOfInteger(UNSORTED_1K);
  }

  public static Integer[] unsorted10KInteger() {
    return copyOfInteger(UNSORTED_10K);
  }

  public static Integer[] unsorted100KInteger() {
    return copyOfInteger(UNSORTED_100K);
  }

  public static Integer[] unsorted1MInteger() {
    return copyOfInteger(UNSORTED_1M);
  }

  // public static Integer[] unsorted10MInteger() {
  // return copyOfInteger(UNSORTED_10M);
  // }
  //
  // public static Integer[] unsorted100MInteger() {
  // return copyOfInteger(UNSORTED_100M);
  // }

  public static String[] unsorted3String() {
    return copyOfString(genNumbers(3));
  }

  public static String[] unsorted1KString() {
    return copyOfString(UNSORTED_1K);
  }

  public static String[] unsorted10KString() {
    return copyOfString(UNSORTED_10K);
  }

  public static String[] unsorted100KString() {
    return copyOfString(UNSORTED_100K);
  }

  public static String[] unsorted1MString() {
    return copyOfString(UNSORTED_1M);
  }

  public static String[] unsorted10MString() {
    return copyOfString(UNSORTED_10M);
  }

  public static String[] unsorted100MString() {
    return copyOfString(genNumbers(100000000));
  }

  public static String[] unsorted1BString() {
    return copyOfString(genNumbers(1000000000));
  }

  private static int[] copyOfInt(int[] toSort) {
    int[] a = new int[toSort.length];
    System.arraycopy(toSort, 0, a, 0, toSort.length);
    return a;
  }

  private static Integer[] copyOfInteger(int[] toSort) {
    Integer[] a = new Integer[toSort.length];
    for (int i = 0; i < toSort.length; i++) {
      a[i] = toSort[i];
    }
    return a;
  }
  
  private static final ObjectPool<String> STRINGS = new ObjectPool<>();

  private static String[] copyOfString(int[] toSort) {
    String[] a = new String[toSort.length];
    for (int i = 0; i < toSort.length; i++) {
      a[i] = STRINGS.intern(Integer.valueOf(Math.abs(toSort[i])).toString());
    }
    return a;
  }

  public static int[] genNumbers(int count) {
    int[] numbers = new int[count];
    for (int i = 0; i < count; i++) {
      numbers[i] = RANDOM.nextInt();
    }
    return numbers;
  }

  public static int[] genNumbers(int count, int max) {
    int[] numbers = new int[count];
    for (int i = 0; i < count; i++) {
      numbers[i] = RANDOM.nextInt(max);
    }
    return numbers;
  }
}
