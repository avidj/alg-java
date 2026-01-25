package org.avidd.math;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Not actually dynamic programming, only memoization, like the all-variant, but
 * adding synchronization. This lends itself to parallelization variants where
 * you just call the recursive definition but avoid recomputation. It's an experiment.
 */
public final class FibonacciDynamic implements Fibonacci {
  private AtomicReference<BigInteger>[] cache;
  private boolean isInitialized = false;
  private final Lock initializing = new ReentrantLock();

  public void init(int n) {
    synchronized ( this ) {
      if ( isInitialized ) {
        return;
      }
    }
    if ( !initializing.tryLock() ) {
      // another thread was faster to start initializing
      synchronized ( this ) {
        while ( !isInitialized ) {
          try {
            this.wait();
          } catch ( InterruptedException e ) {
            // can be ignored
          }
        }
      }
      return; // initialization finished
    }

    try {
      this.cache = new AtomicReference[n + 1];
      Thread init = new Thread(() -> {
        for ( int i = 2; i <= n; i++ ) {
          cache[i] = new AtomicReference<>();
        }
      });
      init.start();
      cache[0] = new AtomicReference(BigInteger.ZERO);
      cache[1] = new AtomicReference(BigInteger.ONE);
      init.join();
      synchronized ( this ) {
        this.isInitialized = true;
        this.notifyAll();
      }
    } catch ( InterruptedException e ) {
      // ignore
    } finally {
      this.initializing.unlock();
    }
    

  }
  
  @Override
  public BigInteger fib(int k) {
    this.init(k);
    if ( k < 0 || k > cache.length ) {
      throw new IllegalArgumentException();
    }
    BigInteger fib = cache[k].get();
    if ( fib != null ) {
      return fib;
    }
    fib = fib(k - 1).add(fib(k - 2));
    cache[k].set(fib);
    return fib;
  }
}
