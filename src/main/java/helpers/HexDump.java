package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/*************************************************************************
 * Compilation: javac HexDump.java Execution: java HexDump < file Dependencies:
 * BinaryStdIn.java Data file: http://introcs.cs.princeton.edu/stdlib/abra.txt
 * 
 * Reads in a binary file and writes out the bytes in hex, 16 per line.
 * 
 * % more abra.txt ABRACADABRA!
 * 
 * % java HexDump 16 < abra.txt 41 42 52 41 43 41 44 41 42 52 41 21 96 bits
 * 
 * 
 * Remark -------------------------- - Similar to the Unix utilities od (octal
 * dump) or hexdump (hexadecimal dump).
 * 
 * % od -t x1 < abra.txt 0000000 41 42 52 41 43 41 44 41 42 52 41 21 0000014
 * 
 * Copyright 2000-2022, Robert Sedgewick and Kevin Wayne
 *************************************************************************/

public class HexDump {

  public static void dump(InputStream in, PrintStream out) throws IOException {
    int BYTES_PER_LINE = 16;

    int i = 0;
    for ( i = 0; in.available() > 0; i++ ) {
      if ( BYTES_PER_LINE == 0 ) {
        in.read();
        continue;
      }
      if ( i == 0 ) {
        out.printf("");
      } else if ( i % BYTES_PER_LINE == 0 )
        out.printf("\n", i);
      else
        out.print(" ");
      char c = (char)in.read();
      out.printf("%02x", c & 0xff);
    }
    if ( BYTES_PER_LINE != 0 ) {
      out.println();
    }
    out.println( ( i * 8 ) + " bits");
  }

  public static void dump(InputStream in, StringBuilder out) throws IOException {
    if ( in.available() > 0 ) {
      char c = (char)in.read();
      out.append(String.format("%02x", c & 0xff));
    }
    while ( in.available() > 0 ) {
      out.append(" ");
      char c = (char)in.read();
      out.append(String.format("%02x", c & 0xff));
    }
  }
}