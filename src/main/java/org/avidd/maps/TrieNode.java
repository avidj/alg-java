package org.avidd.maps;

class TrieNode<V> {
  @SuppressWarnings({"unchecked"})
  final TrieNode<V>[] next = new TrieNode[RWayTrie.RDX_EXTENDED_ASCII];
  Object value;

  @Override
  public String toString() {
    return new StringBuilder("Node ( value = ").append(value).append(", next = ").append(next)
        .append(")").toString();
  }
}