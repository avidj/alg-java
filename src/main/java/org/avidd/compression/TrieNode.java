package org.avidd.compression;

/**
 * Trie node implementation to be used for Huffman coding. This trie node
 * associates a character with its frequency in the input. This piece of
 * information is needed to compute an optimal encoding for the character.
 * 
 * @Immutable
 * @author David Kensche
 */
class TrieNode implements Comparable<TrieNode> {
  final char c;
  final int frequency;
  final TrieNode left;
  final TrieNode right;

  TrieNode(char aC, int aFrequency, TrieNode aLeft, TrieNode aRight) {
    c = aC;
    frequency = aFrequency;
    left = aLeft;
    right = aRight;
  }

  boolean isLeaf() {
    return ( left == null && right == null );
  }

  @Override
  public int compareTo(TrieNode other) {
    return this.frequency - other.frequency;
  }

  @Override
  public String toString() {
    return new StringBuilder("TrieNode( c = ").append(c).append(", frequency = ").append(frequency)
        .append(",  left = ").append(left).append(",  right = ").append(right).append(")")
        .toString();
  }
}
