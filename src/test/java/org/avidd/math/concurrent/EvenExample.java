package org.avidd.math.concurrent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class EvenExample {

  @Test
  public void testNoSynchronization() throws InterruptedException {
    final Even even = new Even();
    List<Callable<AssertionError>> tasks = Arrays.<Callable<AssertionError>> asList(
        new Callable<AssertionError>() {
          @Override
          public AssertionError call() throws Exception {
            try {
              for ( int i = 0; i < Integer.MAX_VALUE; i++ ) {
                even.next();
              }
              return null;
            } catch ( AssertionError e ) {
              return e;
            }
          }
        }, new Callable<AssertionError>() {
          @Override
          public AssertionError call() throws Exception {
            try {
              for ( int i = 0; i < Integer.MAX_VALUE; i++ ) {
                even.next();
              }
              return null;
            } catch ( AssertionError e ) {
              return e;
            }
          }
        });
    ExecutorService threadPool = Executors.newFixedThreadPool(2);
    List<Future<AssertionError>> results = threadPool.invokeAll(tasks);
    MatcherAssert.assertThat(results.get(0), is(not(nullValue())));
  }
}
