package org.avidd.sort;

import static org.avidd.sort.PancakeUtil.printPancakes;
import static org.avidd.sort.PancakeUtil.sortedTail;
import static org.avidd.sort.PancakeUtil.toNaiveList;
import static org.avidd.sort.PancakeUtil.toArray;

import org.avidd.sort.PancakeUtil.NaiveNode;
import org.avidd.sort.PancakeUtil.Node;

/**
 * Naive pancake sort for integer arrays.
 * 
 * Approaches:
 * + greedy pancake sort: bring largest to top, then to bottom, same for smaller stack until done
 * + divide and conquer pancake sort: like merge sort but do a greedy (?) pancake sort instead of merging? 
 *   Can't be efficient
 * + use "appropriate" data structure: a tree index which allows to flip sections in few (constant?) operations?
 *  
 * 
 * TODO:
 * + read "algorithms for prefix reversals"
 * + bounds for sorting by prefix reversal
 * + search for "prefix reversal"
 * + known upper bound is 18N/11
 * + the greedy algorithm is 2n - 3. Thus, the trick would be to use the appropriate data structure
 * + the flipping is expensive, instead of actually reverting the list, the direction of references
 *   should be controlled by a variable. Because each pancake can be flipped a different number of 
 *   times, this variable must be per node. 
 * + it is also expensive to traverse the prefix repeatedly, it would be good to be able to know 
 *   the regions with different sort order direction
 */
class IntPancakeSort implements IntSortStrategy {

  public void sort(int[] a) {
    NaiveNode stack = toNaiveList(a);
    int steps = a.length * a.length;
    printPancakes(stack);
    while ( !sortedTail(stack) && steps-- > 0 ) {
      stack = flipTop(stack);
      printPancakes(stack);
    }
    toArray(a, stack);
  }

  private static NaiveNode flipTop(NaiveNode top) {
    NaiveNode current = top;
    int count = 1;
    // find out the direction, if next is null then it is sorted and we should not be here
    while ( current.value() == top.value() ) {
      current = current.next();
      count++;
    }

    // find the end of the (reverse or not) sorted top of the stack
    if ( top.value() > current.value() ) { 
      while ( current.next() != null && current.value() > current.next().value() ) {
    	    current = current.next();
    	    count++;
      }
    } else {
    	  while ( current.next() != null && current.value() < current.next().value() ) {
    		current = current.next();
    		count++;
    	  }
    }
    
    top = PancakeUtil.flip(top, current, count);
    return top;
  }
  
  private static <T extends Node<T>> int determineDirection(Node<T> top) {
    Node<T> current = top;
    while ( current.next() != null && current.value() == top.value() ) {
      current = current.next();
    }
    if ( current.next() == null ) {
      return 0;
    }
    if ( current.value() > top.value() ) { return 1; }
    if ( current.value() < top.value() ) { return -1; }
    return 0;
  }
}
