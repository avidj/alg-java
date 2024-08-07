package org.avidd.queues;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class ArrayQueueTest {

  @Test
  public void testResize() {
    Queue<Integer> q = new ArrayQueue<>(2);
    q.enqueue(1);
    MatcherAssert.assertThat(q.size(), is(equalTo(1)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    q.enqueue(2);
    MatcherAssert.assertThat(q.size(), is(equalTo(2)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    q.enqueue(3);
    MatcherAssert.assertThat(q.size(), is(equalTo(3)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    q.enqueue(4);
    MatcherAssert.assertThat(q.size(), is(equalTo(4)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    MatcherAssert.assertThat(q.dequeue(), is(equalTo(1)));
    MatcherAssert.assertThat(q.size(), is(equalTo(3)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    MatcherAssert.assertThat(q.dequeue(), is(equalTo(2)));
    MatcherAssert.assertThat(q.size(), is(equalTo(2)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    MatcherAssert.assertThat(q.dequeue(), is(equalTo(3)));
    MatcherAssert.assertThat(q.size(), is(equalTo(1)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    q.enqueue(5);
    MatcherAssert.assertThat(q.size(), is(equalTo(2)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    q.enqueue(6);
    MatcherAssert.assertThat(q.size(), is(equalTo(3)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    MatcherAssert.assertThat(q.dequeue(), is(equalTo(4)));
    MatcherAssert.assertThat(q.size(), is(equalTo(2)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    MatcherAssert.assertThat(q.dequeue(), is(equalTo(5)));
    MatcherAssert.assertThat(q.size(), is(equalTo(1)));
    MatcherAssert.assertThat(q.isEmpty(), is(false));

    MatcherAssert.assertThat(q.dequeue(), is(equalTo(6)));
    MatcherAssert.assertThat(q.size(), is(equalTo(0)));
    MatcherAssert.assertThat(q.isEmpty(), is(true));
  }

}
