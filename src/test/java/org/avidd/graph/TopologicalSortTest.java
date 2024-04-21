package org.avidd.graph;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.avidd.graph.directed.Digraph;
import org.avidd.graph.directed.TopologicalSort;
import org.junit.Assert;
import org.junit.Test;

// TODO: TEST GRAPHS WITH CYCLE
public abstract class TopologicalSortTest {

  @Test
  public final void testTopologicalSort() {
    final Digraph graph = new Digraph().edge(0, 1).edge(0, 2).edge(0, 5).edge(1, 4).edge(3, 2)
        .edge(3, 4).edge(3, 5).edge(3, 6).edge(5, 2).edge(6, 4);
    TopologicalSort ts = createTopologicalSort(graph);
    System.out.println(join(", ", ts.getTopologicalOrder()));
    Assert.assertThat(ts.getTopologicalOrder(), is(equalTo(Arrays.asList(3, 6, 0, 5, 2, 1, 4))));
  }

  @Test
  public final void testCycle1() {
    final Digraph graph = new Digraph().edge(0, 0);
    TopologicalSort ts = createTopologicalSort(graph);
    Assert.assertThat(ts.hasCycle(), is(true));
    Assert.assertThat(ts.getCycle(), is(equalTo(Arrays.asList(0, 0))));
  }

  @Test
  public final void testCycle2() {
    final Digraph graph = new Digraph().edge(0, 1).edge(1, 0);
    TopologicalSort ts = createTopologicalSort(graph);
    Assert.assertThat(ts.hasCycle(), is(true));
    Assert.assertThat(ts.getCycle(), is(equalTo(Arrays.asList(1, 0, 1))));
  }

  @Test
  public final void testCycle3() {
    final Digraph graph = new Digraph().edge(0, 1).edge(1, 2).edge(2, 0);
    TopologicalSort ts = createTopologicalSort(graph);
    Assert.assertThat(ts.hasCycle(), is(true));
    Assert.assertThat(ts.getCycle(), is(equalTo(Arrays.asList(2, 0, 1, 2))));
  }

  private static String join(String delim, List<?> elements) {
    StringBuilder string = new StringBuilder();
    Iterator<?> iterator = elements.iterator();
    if ( iterator.hasNext() ) {
      string.append(iterator.next());
    }
    while ( iterator.hasNext() ) {
      string.append(delim);
      string.append(iterator.next());
    }
    return string.toString();
  }

  protected abstract TopologicalSort createTopologicalSort(Digraph graph);

}
