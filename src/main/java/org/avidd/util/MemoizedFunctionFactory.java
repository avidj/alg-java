package org.avidd.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class MemoizedFunctionFactory {
  public static <T, R> Function<T, R> memoize(Function<T, R> f) {
    return new MemoizedFunction<>(f);
  }
  
  private static class MemoizedFunction<T, R> implements Function<T, R> {
    private final Map<T, R> cache = new ConcurrentHashMap<>();
    private final Function<T, R> f;
    
    MemoizedFunction(Function<T, R> f) {
      assert ( f != null );
      this.f = f;
    }

    @Override
    public R apply(T t) {
      return cache.computeIfAbsent(t, f);
    }
  }
}
