The `helpers` package consists of utilities copied from Sedgewick's algorithms lectures offered on coursera. These packages are not included here. If you want to execute the tests you will have to collect these freely available sources on your own. 

A collection of algorithms and data structure implemented just for fun and recreational programming.

### Mining
* **TODO** Quaternary Search 
* **TODO** Minhashing
* **TODO** Locality Sensitive Hashing
* **TODO** Distance Measures
* **TODO** Frequent Itemsets
* **TODO** A-Priori Algorithm 

### Clustering
* **TODO** k-means Algorithm
* **TODO** BFR Algorithm (Bradley, Fayyad, Reina)
* **TODO** CURE Algorithm
* **TODO** Computational Advertising: Bipartite Graph Matching
* **TODO** Balance Algorithm

### Social Networks
* **TODO** Page Rank

### Community Detection
* **TODO** BIGCLAM
* **TODO** Spectral Graph Partitioning
* **TODO** Spectral Clustering
* **TODO** Trawling

### Data Streams
* **TODO** Counting 1s
* **TODO** Bloom Filters
* **TODO** Sampling
* **TODO** Counting Distinct Elements

### Dimensionality Reduction
* **TODO** SVD
* **TODO** CUR

### Recommender Systems
* **TODO** Collaborative Filtering
* **TODO** Evaluating RS
* **TODO** Latent Factor RS

### Trees and Heaps

* **TODO** Tarjan                       
* **TODO** fibonacci heap               
* **TODO** indexed heap
* **TODO** Patricia trees               
* **TODO** Suffix Trees                 
* **TODO** Cheriyan-Mehlhorn easier one-pass algorithm for SCCs 

### Graphs 

* graph.Dfs
  * cycles
  * connected components
* graph.Bfs
    
* **TODO** Bipartite Graphs                                                                
* **TODO** Finding cycles                                                                  
 * Euler Tour (K�nigsberger Br�ckenproblem)
 * graph isomorphism 
* **TODO** make a graph planar (there is a linear-time DFS-based planarity algorithm)      

## DIGRAPH problems
- shortest path: what is the shortest directed path from s to t
- topological sort: can you draw a digraph so that all edges point upwards
- strong connectivity: is there a directed path between all pairs of vertices    
- transitive closure: for which veritces v and w is there a path from v to w 
- PageRank: What is the importance of a web page
+ Digraph representation complexities
* BFS: multiple sources, shortest paths, web crawler
* Topological Sort: DepthFirstOrder
  * graph.directed.TopologicalSort
  * graph.directed.BlockingTopologicalSort
  * graph.directed.FutureTopologicalSort
* Directed Cycle Detection
  * strongly connected components (connected vs strongly connected)
* Kosaraju-Sharir algorithm
* graph.directed.BlockingStronglyConnectedComponents?

### MSTs
* greedy algorithm
 * Kruskal
 * Prim
 *  array (optimal for dense graphs)
* **TODO** binary heap (much faster for sparse graphs)
* **TODO** 4-way heap (worth the trouble in performance-critical situations)
* **TODO** Fibonacci heap (best in theory but not worth implementing)
* **TODO** Bor�vka                      

### Shortest Paths
* graph.Dijkstra
* graph.BellmanFord

### String Sorts
* LSD Radix Sort
* MSD Radix Sort
* **TODO** 3-way radix quicksort          
* **TODO** suffix arrays (only LSD variant not 3-way string quicksort)

### Tries
* R-way tries
  * classic.maps.RWayTrie
* ternary search tries
  * classic.maps.TernaryTree
* **TODO** character-based operations    

### Substring Search
* brute force
  * algs.string.BruteForce
  * algs.string.BruteForceExplicitBackup
* Knuth-Morris Pratt
  * algs.string.KnuthMorrisPratt
* Boyer-Moore                     *
* Rabin-Karp
  * algs.string.RabinKarpLasVegas
  * algs.string.RabinKarpLasVegasIncremental
  * algs.string.RabinKarpMonteCarlo
  * algs.string.RabinKarpMonteCarloIncremental

### Regular Expressions
* **TODO** REs and NFAs                    

### Data Compression
* run-length coding
* Huffman compression
    compression.Huffman
* **TODO** LZW compression                 
* **TODO** Deflate (use R-Way Trie)

### Max-Flow
* Ford-Fulkerson
      graph.weighted.FordFulkerson
* **TODO** maxflow-mincut                  

### Linear Programming
* **TODO** Brewer                          
* **TODO** Simplex                         

* **TODO** A*
* **TODO** IDA*
