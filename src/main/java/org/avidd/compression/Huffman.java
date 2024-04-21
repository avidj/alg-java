package org.avidd.compression;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import org.avidd.util.io.BinaryInputStream;
import org.avidd.util.io.BinaryOutputStream;

/**
 * Huffman code compression algorithm.
 * 
 * @author David Kensche
 */
public class Huffman implements CompressionStrategy {
  private final int radix;
  
  /**
   * Create a new instance of this compression strategy assuming the given radix for the input 
   * encoding. 
   * @param aRadix the radix for the input encoding
   */
  Huffman(int aRadix) {
    radix = aRadix;
  }

  @Override
  public void extract(InputStream compressed, final OutputStream extracted) throws IOException {
    final BinaryInputStream in = new BinaryInputStream(compressed);
    final BinaryOutputStream out = new BinaryOutputStream(extracted);
    final TrieNode trie = readTrie(in);
    System.out.println("trie-2:\n" + trie);
    System.out.println("encoding:\n" + printEncoding(toEncoding(trie)));
    // final int numCharacters = (int)in.readDouble();
    final int numCharacters = in.readInt();
    System.out.println("numCharacters " + numCharacters);
    assert ( numCharacters == 12 );

    for ( int i = 0; i < numCharacters; i++ ) {
      TrieNode current = trie;
      while ( !current.isLeaf() ) {
        if ( !in.readBit() ) {
          current = current.left;
        } else {
          current = current.right;
        }
      }
      System.out.write(current.c);
      out.write(current.c, 8);
    }
    out.flush();
  }

  @Override
  public int compress(char[] extracted, final OutputStream compressed) throws IOException {
    // first build the trie and find out how many characters there are
    final BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(new String(
        extracted).getBytes()));
    final TrieNode trie = buildTrie(in);
    final BinaryOutputStream out = new BinaryOutputStream(compressed);
    final List<Boolean>[] encoding = toEncoding(trie);

    writeTrie(trie, out);
    out.write(extracted.length);
    int count = 0;
    for ( char c : extracted ) {
      for ( Boolean bit : encoding[c] ) {
        out.write(bit.booleanValue());
        count++;
      }
    }
    out.flush();
    return count;
  }

  @Override
  public int compress(InputStream extracted, final OutputStream compressed) throws IOException {
    // first build the trie and find out how many characters there are
    final String string = readString(extracted);
    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(string.getBytes()));
    TrieNode trie = buildTrie(in);

    in = new BinaryInputStream(new ByteArrayInputStream(string.getBytes()));
    final BinaryOutputStream out = new BinaryOutputStream(compressed);

    List<Boolean>[] encoding = toEncoding(trie);
    writeTrie(trie, out);
    out.write(string.length());
    char c;
    int count = 0;
    while ( !in.isEmpty() ) {
      c = in.readChar(8);
      for ( Boolean bit : encoding[c] ) {
        out.write(bit.booleanValue());
        count++;
      }
    }
    out.flush();
    return count;
  }

  private String printEncoding(List<Boolean>[] encoding) {
    StringBuilder string = new StringBuilder();
    for ( int i = 0; i < encoding.length; i++ ) {
      if ( encoding[i] != null ) {
        string.append((char)i).append("\t");
        appendCode(string, encoding[i]);
        string.append("\n");
      }
    }
    return string.toString();
  }

  private void appendCode(StringBuilder string, List<Boolean> code) {
    for ( Boolean bit : code ) {
      string.append(bit ? 1 : 0);
    }

  }

  private String readString(InputStream extracted) throws IOException {
    StringBuilder string = new StringBuilder();
    BufferedReader in = new BufferedReader(new InputStreamReader(extracted));
    int c;
    while ( ( c = in.read() ) != -1 ) {
      string.append((char)c);
    }
    return string.toString();
  }

  private List<Boolean>[] toEncoding(TrieNode trie) {
    Stack<Boolean> current = new Stack<Boolean>();
    @SuppressWarnings({"unchecked"})
    List<Boolean>[] encoding = new List[radix];
    dfs(trie, current, encoding);
    return encoding;
  }

  private void dfs(TrieNode node, Stack<Boolean> current, List<Boolean>[] encoding) {
    if ( node.isLeaf() ) {
      encoding[node.c] = new ArrayList<Boolean>(current);
      // Collections.reverse(encoding[node.c]);
    } else {
      current.push(Boolean.FALSE);
      dfs(node.left, current, encoding);
      current.pop();

      current.push(Boolean.TRUE);
      dfs(node.right, current, encoding);
      current.pop();
    }
  }

  private TrieNode buildTrie(BinaryInputStream in) throws IOException {
    PriorityQueue<TrieNode> queue = new PriorityQueue<TrieNode>();
    int[] frequencies = new int[radix];
    char c;
    while ( !in.isEmpty() ) {
      c = in.readChar(8);
      frequencies[c]++;
    }
    for ( int i = 0; i < radix; i++ ) {
      if ( frequencies[i] > 0 ) {
        queue.add(new TrieNode((char)i, frequencies[i], null, null));
      }
    }
    while ( queue.size() > 1 ) {
      TrieNode left = queue.remove();
      TrieNode right = queue.remove();
      queue.add(new TrieNode('\0', left.frequency + right.frequency, left, right));
    }
    return queue.remove();
  }

  private static void writeTrie(TrieNode trie, BinaryOutputStream out) throws IOException {
    if ( trie.isLeaf() ) {
      out.write(true);
      out.write(trie.c, 8);
    } else {
      out.write(false);
      writeTrie(trie.left, out);
      writeTrie(trie.right, out);
    }
  }

  private static TrieNode readTrie(BinaryInputStream in) throws IOException {
    if ( in.readBit() ) {
      char c = in.readChar(8);
      return new TrieNode(c, 0, null, null);
    } else {
      TrieNode left = readTrie(in);
      TrieNode right = readTrie(in);
      return new TrieNode('\0', 0, left, right);
    }
  }
}