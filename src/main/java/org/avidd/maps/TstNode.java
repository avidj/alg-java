package org.avidd.maps;

class TstNode {
  final char c;
  TstNode parent;
  TstNode left;
  TstNode middle;
  TstNode right;
  Object value;

  TstNode(TstNode parent, char character) {
    this.parent = parent;
    c = character;
  }

  TstNode() {
    c = (char)-1;
  }

  @Override
  public String toString() {
    return new StringBuilder("Node ( character = '").append(c).append("', value = ")
        .append(value).append(")").toString();
  }
}