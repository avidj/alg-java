package org.avidd.graph.weighted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Job scheduling algorithm using topological sort (CPM, critical path method) for computing the 
 * single-source shortest paths in a graph defined as follows:
 * <ul>
 *   <li>There is a node 0 representing the start of the schedule.</li>
 *   <li>There is a node sink (jobs.size() * 2 + 1) representing the end of the schedule.</li>
 *   <li>For each job there are two nodes represing the start and the end of the job connected by an
 *     edge weighted with the job duration.</li>
 *   <li>The source is connected to each job start by a 0-weighted edge.</li>
 *   <li>Each job end is connected to the sink by a 0-weighted edge.</li>
 *   <li>A 0-weighhted edge for each precedence constraint orginating from the end of the 
 *     predecessor job to the start of the successor job.</li>
 *   <li>The edge weights are negated, so we can look for shortest paths so as to actually get the
 *     longest paths.</li>
 * </ul> 
 */
class Schedule {

  private final DagShortestPaths sps;

  /**
   * @param jobs the jobs to be scheduled
   */
  Schedule(Collection<Job> jobs) {
    /* Create the DAG representing the scheduling problem. */
    EdgeWeightedDigraph g = new EdgeWeightedDigraph();
    int source = 0;
    int sink = jobs.size() * 2 + 1;
    for ( Job job : jobs ) {
      g.edge(source, job.id, 0.0);
      g.edge(job.id, sink, 0.0);
      g.edge(job.id, job.id * 2, job.duration);
      for ( Job after : job.mustCompleteBefore ) {
        g.edge(job.id * 2, after.id, 0.0);
      }
    }
    g = g.negateEdges();
    sps = new DagShortestPaths(g, source);
  }

  double startTime(Job job) {
    return -sps.distTo(job.id);
  }

  /**
   * A job has an identifier, a duration and a set of precedence constraints. These are the jobs 
   * that may only start after this job has completed. 
   */
  static class Job {
    private final int id;
    private final double duration;
    private final Collection<Job> mustCompleteBefore;

    public Job(int aId, double aDuration, Collection<Job> aMustCompleteBefore) {
      id = aId;
      duration = aDuration;
      mustCompleteBefore = Collections.unmodifiableList(new ArrayList<Job>(aMustCompleteBefore));
    }
  }

}
