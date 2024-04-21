Basics
* Quaternary Search
* Minhashing
* Locality Sensitive Hashing
* Distance Measures
* Frequent Itemsets
* A-Priori Algorithm 

Clustering
* k-means Algorithm
* BFR Algorithm (Bradley, Fayyad, Reina)
* CURE Algorithm
* Computational Advertising: Bipartite Graph Matching
* Balance Algorithm

Social Networks
* Page Rank

Community Detection
* BIGCLAM
* Spectral Graph Partitioning
* Spectral Clustering
* Trawling

Data Streams
* Counting 1s
* Bloom Filters
* Sampling
* Counting Distinct Elements

Dimensionality Reduction
* SVD
* CUR

Recommender Systems
* Collaborative Filtering
* Evaluating RS
* Latent Factor RS


GENERAL

Tarjan                       *
fibonacci heap               *
indexed heap
Patricia trees               *
Suffix Trees                 *
Cheriyan-Mehlhorn easier one-pass algorithm for SCCs *

algorithms-2
* DFS
    graph.Dfs
* BFS
    graph.Bfs
* Connected Components
    graph.ConnectedComponents
    
Challenges
* Bipartite Graphs                                                                *
* Finding cycles                                                                  *
 Euler Tour (K�nigsberger Br�ckenproblem)
 graph isomorphism is an open problem (unknown complexity)
* make a graph planar (there is a linear-time DFS-based planarity algorithm)      *

DIGRAPH problems
- shortest path: what is the shortest directed path from s to t
- topological sort: can you draw a digraph so that all edges point upwards
- strong connectivity: is there a directed path between all pairs of vertices    
- transitive closure: for which veritces v and w is there a path from v to w 
- PageRank: What is the importance of a web page
+ Digraph representation complexities
* DFS
* BFS: multiple sources, shortest paths, web crawler
* Topological Sort: DepthFirstOrder
    graph.directed.TopologicalSort
    graph.directed.BlockingTopologicalSort
    graph.directed.FutureTopologicalSort
* Directed Cycle Detection                *
strongly connected components (connected vs strongly connected)
* Kosaraju-Sharir algorithm
    graph.directed.BlockingStronglyConnectedComponents?

MSTs
* greedy algorithm
** Kruskal
** Prim
*** array (optimal for dense graphs)
*** binary heap (much faster for sparse graphs)
*** 4-way heap (worth the trouble in performance-critical situations)
*** Fibonacci heap (best in theory but not worth implementing)
** Bor�vka                      *

Shortest Paths
* Dijkstra
     graph.Dijkstra
* Bellman-Ford
     graph.BellmanFord


String Sorts
* LSD Radix Sort
* MSD Radix Sort
* 3-way radix quicksort          *
* suffix arrays                 (*) (only LSD variant not 3-way string quicksort)

Tries
* R-way tries
    classic.maps.RWayTrie
* ternary search tries
    classic.maps.TernaryTree
* character-based operations    *

Substring Search
* brute force
    algs.string.BruteForce
    algs.string.BruteForceExplicitBackup
* Knuth-Morris Pratt
    algs.string.KnuthMorrisPratt
* Boyer-Moore                     *
* Rabin-Karp
    algs.string.RabinKarpLasVegas
    algs.string.RabinKarpLasVegasIncremental
    algs.string.RabinKarpMonteCarlo
    algs.string.RabinKarpMonteCarloIncremental

Regular Expressions
* REs and NFAs                    *

Data Compression
* run-length coding
* Huffman compression
    compression.Huffman
* LZW compression                 *
* Deflate                         * (from job-interview: use R-Way Trie)

Max-Flow
* Ford-Fulkerson
      graph.weighted.FordFulkerson
* maxflow-mincut                  *

Linear Programming
* Brewer                          *
* Simplex                         *

Additionally?
A*
IDA*
