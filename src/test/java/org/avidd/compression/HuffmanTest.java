package org.avidd.compression;

public class HuffmanTest extends CompressionStrategyTest {

  @Override
  protected CompressionStrategy getStrategy() {
    return new Huffman(256);
  }

}
