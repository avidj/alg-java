package org.avidd.sort;

import java.io.IOException;
import java.util.Arrays;

import org.avidd.util.FileUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class KillJavaSortTest {

  @Test
  // @Expected ( StackOverflowError.class )
  @Disabled
  public void testKill10K() throws NumberFormatException, IOException {
    int[] killer = FileUtil.readInts(10000, "antiquicksort10K.txt");
    System.err.println("killer = " + Arrays.toString(killer));
    Arrays.sort(killer);
  }

  @Test
  // @Expected ( StackOverflowError.class )
  @Disabled
  public void testKill250K() throws NumberFormatException, IOException {
    int[] killer = FileUtil.readInts(250000, "antiquicksort250K.txt");
    System.err.println("killer = " + Arrays.toString(killer));
    Arrays.sort(killer);
  }

  @Test
  // @Expected ( StackOverflowError.class )
  @Disabled
  public void testKill1M() throws NumberFormatException, IOException {
    int[] killer = FileUtil.readInts(1000000, "antiquicksort1M.txt");
    System.err.println("killer = " + Arrays.toString(killer));
    Arrays.sort(killer);
  }
}
