package org.avidd.math;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class PowTest {
  
  @Test
  public void test0() {
    Pow pow = new Pow();
    assertThat(pow.pow(1, 0), is (1L));
    assertThat(pow.pow(5, 0), is (1L));
    assertThat(pow.pow(-5, 0), is (1L));
    assertThat(pow.pow(100, 0), is (1L));
  }

  @Test
  public void test1() {
    Pow pow = new Pow();
    assertThat(pow.pow(1, 1), is (1L));
    assertThat(pow.pow(5, 1), is (5L));
    assertThat(pow.pow(-5, 1), is (-5L));
    assertThat(pow.pow(100, 1), is (100L));
  }

  @Test
  public void test2() {
    Pow pow = new Pow();
    assertThat(pow.pow(1, 2), is (1L));
    assertThat(pow.pow(5, 2), is (25L));
    assertThat(pow.pow(-5, 2), is (25L));
    assertThat(pow.pow(100, 2), is (10000L));
  }

  @Test
  public void test3() {
    Pow pow = new Pow();
    assertThat(pow.pow(1, 3), is (1L));
    assertThat(pow.pow(5, 3), is (125L));
    assertThat(pow.pow(-5, 3), is (-125L));
    assertThat(pow.pow(100, 3), is (1000000L));
  }
  
  @Test
  public void test4Over6() {
    Pow pow = new Pow();
    assertThat(pow.pow(6, 4), is (1296L));
  }

  @Test
  public void test6Over6() {
    Pow pow = new Pow();
    assertThat(pow.pow(6, 6), is (46656L));
  }
}
