package org.avidd.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility for loading test data files into arrays.
 *  
 * @author David Kensche
 */
public final class FileUtil {
  
  private FileUtil() { /* hidden utility class constructor */ }

  /**
   * Read a list of strings by splitting each line by whitespace characters.
   * @param filename the file name
   * @return the list of strings read from the file
   * @throws RuntimeException if any exception occurs during file access
   */
  public static List<String> readStrings(String filename) {    
    List<String> strings = new ArrayList<String>();
    try ( BufferedReader in = 
          new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8")) ) {
      String line;
      while ( ( line = in.readLine() ) != null ) {
        line = line.replaceAll("\"'\\(\\)\\[\\]-_!\\?\\.:,", "");
        String[] tokens = line.split("\\s]");//(\\.|\\s)*");
        strings.addAll(Arrays.asList(tokens));
      }
    } catch ( FileNotFoundException e ) {
      throw new RuntimeException(e);
    } catch ( IOException e ) {
      throw new RuntimeException(e);
    }
    List<String> result = new ArrayList<String>(strings.size());
    for ( String s : strings ) {
      s = s.trim();
      if ( s.length() > 0 ) {
        result.add(s);
      }
    }
    return result;
  }

  /**
   * Read a file of integers by one line per integer. The first line of the file is assumed to 
   * define the number of following lines.
   * @param filename the file name
   * @return an array of integers read from the file 
   * @throws NumberFormatException if a line cannot be parsed to an integer
   * @throws IOException if an error occurs in lower layers
   */
  public static int[] readInts(String filename) throws NumberFormatException, IOException {
    int[] ints = null;
    int pos = 0;
    try ( BufferedReader in = new BufferedReader(new FileReader(filename)) ) {
      String line = in.readLine();
      ints = new int[Integer.parseInt(line)];
      while ( ( line = in.readLine() ) != null ) {
        ints[pos++] = Integer.parseInt(line);
      }
      return ints;
    }
  }

  /**
   * Read a file of integers by one line per integer.
   * @param size the number of lines in the file
   * @param filename the file name
   * @return an array of integers read from the file 
   * @throws NumberFormatException if a line cannot be parsed to an integer
   * @throws IOException if an error occurs in lower layers
   */
  public static int[] readInts(int size, String filename) 
  throws NumberFormatException, IOException {
    int[] ints = null;
    int pos = 0;
    try ( BufferedReader in = new BufferedReader(new FileReader(filename)) ) {
      ints = new int[size];
      String line = null;
      while ( ( line = in.readLine() ) != null ) {
        ints[pos++] = Integer.parseInt(line);
      }
      return ints;
    }
  }
}
