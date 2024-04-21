package org.avidd.graph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.avidd.graph.directed.Digraph;
import org.avidd.graph.directed.FutureTopologicalSort;
import org.avidd.graph.directed.TopologicalSort;

public class FutureTopologicalSortTest extends TopologicalSortTest {

  @Override
  protected TopologicalSort createTopologicalSort(Digraph graph) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    return new FutureTopologicalSort(graph, executorService);
  }

}
