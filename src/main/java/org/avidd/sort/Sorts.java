package org.avidd.sort;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Sorts {
  private static final Logger LOG = LogManager.getLogger(Sorts.class);
  static final ForkJoinPool POOL = new ForkJoinPool();
  private static final SortStrategy BUBBLE = new BubbleSort();
  @SuppressWarnings ( "unused" )
  private static final SortStrategy SHELL = new ShellSort();
  private static final SortStrategy COUNT = new CountSort();
  private static final SortStrategy INSERTION = new InsertionSort();
  private static final SortStrategy SELECTION = new SelectionSort();
  private static final SortStrategy SHAKER = new ShakerSort();
  private static final SortStrategy HEAP = new HeapSort();
  private static final SortStrategy MERGE = new MergeSort();
  private static final SortStrategy OPTIMAL_MERGE = new OptimalMergeSort();
  private static final SortStrategy QUICK = new QuickSort();
  private static final SortStrategy TW_QUICK = new ThreeWayQuickSort();
  
  private static final SortStrategy MERGE_PARALLEL = new ParallelMergeSort(1000);
  private static final SortStrategy QUICK_PARALLEL = new ParallelQuickSort(1000);
  private static final SortStrategy TW_QUICK_PARALLEL = new ParallelThreeWayQuickSort(1000);

  private static final IntSortStrategy BUCKET_INT = new IntBucketSort();
  private static final IntSortStrategy HEAP_INT = new IntHeapSort();
  private static final IntSortStrategy INSERTION_INT = new IntInsertionSort();
  private static final IntSortStrategy SELECTION_INT = new IntSelectionSort();
  private static final IntSortStrategy MERGE_INT = new IntMergeSort();
  private static final IntSortStrategy QUICK_INT = new IntQuickSort();
  private static final IntSortStrategy SHAKER_INT = new IntShakerSort();
  private static final IntSortStrategy SHELL_INT = new IntShellSort();
  private static final IntSortStrategy TW_QUICK_INT = new IntThreeWayQuickSort();

  private static final IntSortStrategy MERGE_INT_PARALLEL = new ParallelIntMergeSort(1000);
  private static final IntSortStrategy QUICK_INT_PARALLEL = new ParallelIntQuickSort(1000);
//  private static final IntSortStrategy TW_QUICK_INT_PARALLEL = new ParallelTWQuickIntSort(1000);

  private static final RadixSortStrategy STRAIGHT_RADIX = new StraightRadix();
  private static final RadixSortStrategy LSD_RADIX = new LsdRadixSort();
  private static final RadixSortStrategy MSD_RADIX = new MsdRadixSort();
  
  private Sorts() { /* hidden utility class constructor */ }

  public static <T> void bubbleSort(Comparator<T> comparator, T[] a) {
    BUBBLE.sort(a, comparator);
  }
  
  public static <T> void countSort(Comparator<T> comparator, T[] a) {
    COUNT.sort(a, comparator);
  }
  
  public static <T> void insertionSort(Comparator<T> comparator, T[] a) {
    INSERTION.sort(a, comparator);
  }
  
  public static <T> void selectionSort(Comparator<T> comparator, T[] a) {
    SELECTION.sort(a, comparator);
  }

  public static <T> void shakerSort(Comparator<T> comparator, T[] a) {
    SHAKER.sort(a, comparator);
  }

  public static <T> void heapSort(Comparator<T> comparator, T[] a) {
    HEAP.sort(a, comparator);
  }

  public static <T> void mergeSort(Comparator<T> comparator, T[] a) {
    MERGE.sort(a, comparator);
  }

  public static <T> void optimizedMergeSort(Comparator<T> comparator, T[] a) {
    OPTIMAL_MERGE.sort(a, comparator);
  }

  public static <T> void quickSort(Comparator<T> comparator, T[] a) {
    QUICK.sort(a, comparator);
  }

  public static <T> void threeWayQuickSort(Comparator<T> comparator, T[] a) {
    TW_QUICK.sort(a, comparator);
  }

  /* ***** int sorts ***** */

  public static <T> void bucketSort(int[] a) {
    BUCKET_INT.sort(a);
  }

  public static <T> void heapSort(int[] a) {
    HEAP_INT.sort(a);
  }

  public static <T> void insertionSort(int[] a) {
    INSERTION_INT.sort(a);
  }

  public static <T> void selectionSort(int[] a) {
    SELECTION_INT.sort(a);
  }

  public static <T> void mergeSort(int[] a) {
    MERGE_INT.sort(a);
  }

  public static <T> void quickSort(int[] a) {
    QUICK_INT.sort(a);
  }

  public static <T> void shakerSort(int[] a) {
    SHAKER_INT.sort(a);
  }

  public static <T> void shellSort(int[] a) {
    SHELL_INT.sort(a);
  }

  public static <T> void parallelMergeSort(int[] a) {
    MERGE_INT_PARALLEL.sort(a);
  }

  public static <T> void parallelQuickSort(int[] a) {
    QUICK_INT_PARALLEL.sort(a);
  }

  public static <T> void threeWayQuickSort(int[] a) {
    TW_QUICK_INT.sort(a);
  }

  /* ***** string sorts ***** */

  public static void straightRadixSort(String[] a) {
    STRAIGHT_RADIX.sort(a);
  }

  public static void lsdRadixSort(String[] a) {
    LSD_RADIX.sort(a);
  }

  public static void msdRadixSort(String[] a) {
    MSD_RADIX.sort(a);
  }

  /* ***** PARALLEL SORTS ***** */

  public static <T> void parallelMergeSort(Comparator<T> comparator, T[] a) {
    MERGE_PARALLEL.sort(a, comparator);
  }

  public static <T> void parallelQuickSort(Comparator<T> comparator, T[] a) {
    QUICK_PARALLEL.sort(a, comparator);
  }

  public static <T> void parallelThreeWayQuickSort(Comparator<T> comparator, T[] a) {
    TW_QUICK_PARALLEL.sort(a, comparator);
  }

  /* ***** list sorts ***** */
  
  @SuppressWarnings("unchecked")
  public static <T> void quickSort(Comparator<T> comparator, List<T> a) {
    QUICK.sort(a.toArray((T[])a.toArray()), comparator);
  }

  public static void lsdRadixSort(List<String> a) {
    LSD_RADIX.sort((String[])a.toArray());
  }

  public static void shutDownNow() {    
    POOL.shutdownNow();
  }

  public static void shutDown(long timeout) {    
    try {
      POOL.awaitTermination(timeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      LOG.debug("Interrupted during shutdown.", e);
    }
    shutDownNow();
  }
}
