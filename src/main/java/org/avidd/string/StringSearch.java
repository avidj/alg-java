package org.avidd.string;

/**
 * A string search strategy searches for a given pattern in a given text and
 * returns the first position of that pattern in the text or -1 if it does no
 * occur in the text.
 * 
 * @author David Kensche
 */
interface StringSearch {
  public int search(String pattern, String text);
}
