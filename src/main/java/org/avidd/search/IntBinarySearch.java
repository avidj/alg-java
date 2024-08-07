package org.avidd.search;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author david
 */
public class IntBinarySearch {
  
  public static int binSearch(int[] a, int e) {
    int l = 0; 
    int r = a.length - 1;
    while ( l < r ) {
      int m = ( l + r ) / 2;
      if ( a[m] == e ) return m;
      if ( a[m] > e )  r = m - 1;
      if ( a[m] < e )  l = m + 1;
    }
    return -1;
  }
  
}
