package org.avidd.queues;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

public class ArrayQueueTest {

  @Test
  public void testResize() {
    Queue<Integer> q = new ArrayQueue<Integer>(2);
    q.enqueue(1);
    Assert.assertThat(q.size(), is(equalTo(1)));
    Assert.assertThat(q.isEmpty(), is(false));

    q.enqueue(2);
    Assert.assertThat(q.size(), is(equalTo(2)));
    Assert.assertThat(q.isEmpty(), is(false));

    q.enqueue(3);
    Assert.assertThat(q.size(), is(equalTo(3)));
    Assert.assertThat(q.isEmpty(), is(false));

    q.enqueue(4);
    Assert.assertThat(q.size(), is(equalTo(4)));
    Assert.assertThat(q.isEmpty(), is(false));

    Assert.assertThat(q.dequeue(), is(equalTo(1)));
    Assert.assertThat(q.size(), is(equalTo(3)));
    Assert.assertThat(q.isEmpty(), is(false));

    Assert.assertThat(q.dequeue(), is(equalTo(2)));
    Assert.assertThat(q.size(), is(equalTo(2)));
    Assert.assertThat(q.isEmpty(), is(false));

    Assert.assertThat(q.dequeue(), is(equalTo(3)));
    Assert.assertThat(q.size(), is(equalTo(1)));
    Assert.assertThat(q.isEmpty(), is(false));

    q.enqueue(5);
    Assert.assertThat(q.size(), is(equalTo(2)));
    Assert.assertThat(q.isEmpty(), is(false));

    q.enqueue(6);
    Assert.assertThat(q.size(), is(equalTo(3)));
    Assert.assertThat(q.isEmpty(), is(false));

    Assert.assertThat(q.dequeue(), is(equalTo(4)));
    Assert.assertThat(q.size(), is(equalTo(2)));
    Assert.assertThat(q.isEmpty(), is(false));

    Assert.assertThat(q.dequeue(), is(equalTo(5)));
    Assert.assertThat(q.size(), is(equalTo(1)));
    Assert.assertThat(q.isEmpty(), is(false));

    Assert.assertThat(q.dequeue(), is(equalTo(6)));
    Assert.assertThat(q.size(), is(equalTo(0)));
    Assert.assertThat(q.isEmpty(), is(true));
  }

}
