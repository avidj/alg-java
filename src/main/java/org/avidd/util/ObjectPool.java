package org.avidd.util;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * An object pool for caching immutable objects. Objects are stored in a weak hash map. To use, 
 * create a static factory for the immutable class and, instead of returning freshly constructed
 * objects, return the result of {@link #intern(Object)}.
 * 
 * @author David Kensche
 *
 * @param <T> the type of cached objects
 */
public class ObjectPool<T> {
  private final Map<T, WeakReference<T>> pool = new WeakHashMap<>();

  /**
   * Returns a cached equal instance of the input object or caches the input itself and returns it
   * again.
   * 
   * @param object the object to internalize
   * @return the cached object if it exists, otherwise the input
   */
  public T intern(T object) {
    WeakReference<T> ref = pool.get(object);
    if ( ref != null ) {
      T result = ref.get();
      if ( result != null ) {
        return result;
      }
    }
    pool.put(object, new WeakReference<>(object));
    return object;
  }
}
