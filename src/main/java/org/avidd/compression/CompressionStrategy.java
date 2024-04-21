package org.avidd.compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for compression strategies. 
 * 
 * @author David Kensche
 */
public interface CompressionStrategy {

  /**
   * Extract the contents of the given input stream and write the result to the given output stream.
   * @param compressed the compressed content
   * @param extracted the extracted content
   * @throws IOException if an error occurs during I/O
   */
  void extract(InputStream compressed, OutputStream extracted) throws IOException;

  /**
   * Compress the contents of the given input stream and write the result to the given output 
   * stream.
   * @param extracted the original input
   * @param compressed the compressed output
   * @return the number of bits written to the output stream
   * @throws IOException if an error occurs during I/O
   */
  int compress(InputStream extracted, OutputStream compressed) throws IOException;

  /**
   * Compress the contents of the given char array and write the result to the given output 
   * stream.
   * @param extracted the original input
   * @param compressed the compressed output
   * @return the number of bits written to the output stream
   * @throws IOException if an error occurs during I/O
   */
  int compress(char[] extracted, OutputStream compressed) throws IOException;

}
