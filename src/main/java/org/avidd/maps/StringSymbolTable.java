package org.avidd.maps;

import java.util.Map;
import java.util.Set;

public interface StringSymbolTable<V> extends SymbolTable<String, V> {

  /**
   * @param aKey
   * @return the number of keys less than aKey
   */
  public int rank(String aKey);

  /**
   * @param aKey1
   * @param aKey2
   * @return the entries between inclusive aKey1 and aKey2
   */
  public Map<String, V> range(String aKey1, String aKey2);

  public void deleteMin();

  public void deleteMax();

  /**
   * @param aPrefix
   *          the prefix required in the keys
   * @return all keys stored in the table that have the given prefix
   */
  public Set<String> keysWithPrefix(String aPrefix);

  /**
   * @param query
   *          the query string to search for
   * @return the longest prefix of the query that exists as a key in the symbol
   *         table
   */
  public String longestPrefix(String query);

}
