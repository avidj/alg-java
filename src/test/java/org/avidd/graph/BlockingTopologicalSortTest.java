package org.avidd.graph;

import org.avidd.graph.directed.BlockingTopologicalSort;
import org.avidd.graph.directed.Digraph;
import org.avidd.graph.directed.TopologicalSort;

public class BlockingTopologicalSortTest extends TopologicalSortTest {

  @Override
  protected TopologicalSort createTopologicalSort(Digraph graph) {
    return new BlockingTopologicalSort(graph);
  }

}
