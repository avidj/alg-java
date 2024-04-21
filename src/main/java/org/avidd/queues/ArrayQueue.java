package org.avidd.queues;

import java.util.NoSuchElementException;

public class ArrayQueue<T> implements Queue<T> {
  private T[] items;
  private int head;
  private int tail;

  @SuppressWarnings("unchecked")
  public ArrayQueue(int cap) {
    items = (T[])new Object[cap];
    head = 0;
    tail = 0;
  }

  @Override
  public void enqueue(T item) {
    if ( ( tail + 1 ) % items.length == head ) {
      resize(2 * items.length);
    }
    items[tail] = item;
    tail = ( tail + 1 ) % items.length;
  }

  @Override
  public T dequeue() {
    if ( isEmpty() ) {
      throw new NoSuchElementException();
    }
    T item = items[head];
    items[head] = null;
    head = ( head + 1 ) % items.length;
    if ( size() <= items.length / 4 ) {
      resize(items.length / 2);
    }
    return item;
  }

  private void resize(int cap) {
    @SuppressWarnings("unchecked")
    T[] resized = (T[])new Object[cap];
    int n = size();
    for ( int i = 0; i < n; i++ ) {
      resized[i] = items[ ( head + i ) % items.length];
    }
    head = 0;
    tail = n;
    items = resized;
  }

  @Override
  public boolean isEmpty() {
    return head == tail;
  }

  @Override
  public int size() {
    return ( items.length + tail - head ) % items.length;
    // if ( tail < head ) {
    // return tail + items.length - head;
    // } else {
    // return tail - head;
    // }
  }
}
