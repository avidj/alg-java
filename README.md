# alg-java

Algorithms and data structures implemented from scratch in Java. I wrote this to refresh and deepen
knowledge, and to have a place where I can try design ideas without production constraints. It is not a
library and I do not recommend depending on it. `java.util` is faster and better tested.

About 90 implementations covering roughly 60 distinct algorithms and data structures.

## Contents

| Area | Implementations | What is in there |
|---|---|---|
| Sorting | 33 | insertion, selection, bubble, shaker, shell, heap, merge, optimal merge, quick, three-way quick, quickselect, partial sort, counting, bucket, LSD/MSD/straight radix, inversion counting |
| Graphs | 16 | BFS, DFS (recursive and explicit stack), Dijkstra, Bellman-Ford, DAG shortest paths, connected components, strongly connected components, topological sort, Ford-Fulkerson max flow, Kruskal, Prim (lazy and eager), scheduling |
| Symbol tables and trees | 8 | left-leaning red-black BST, Patricia tree, R-way trie, ternary search tree, suffix tree, Fenwick tree, linear probing and separate chaining hash maps |
| Strings | 8 | Knuth-Morris-Pratt, Rabin-Karp Las Vegas (plain and incremental), brute force with and without explicit backup, Levenshtein, longest repeated substring |
| Heaps and queues | 5 | binary min/max heap, indexed min/max heap, array queue |
| Union-find | 4 | quick-find, quick-union, weighted quick-union, weighted quick-union with path compression |
| Math | 8 | six sequential Fibonacci strategies plus a ForkJoin one, fast exponentiation |
| Search | 3 | binary search over arrays, ints, and inverted arrays |
| Compression, geometry | 2 | Huffman coding, Graham scan |

The implementation count is higher than the algorithm count on purpose. Several algorithms exist in more
than one form: a generic version and an `int`-specialized one, or a sequential version and a ForkJoin
parallel one. They are meant to be compared against each other, not to be deduplicated.

## How it is organized

Every family sits behind a strategy interface: `SortStrategy`, `IntSortStrategy`, `RadixSortStrategy`,
`MstStrategy`, `CompressionStrategy`, `StringSearch`, `UnionFind`, `TopologicalSort`, `SymbolTable`.
Implementations are interchangeable, and the tests are written against the interface rather than against
the implementation.

Consequently the test suites are conformance suites. `AbstractSortTest` and `AbstractIntSortTest` define
the behaviour every sort must satisfy, time each run, and are subclassed once per implementation.
`SystemSortTest` and `IntSystemSortTest` put `java.util.Arrays.sort` through the same suite, so the JDK
is a baseline in the same harness as everything else.

Sorting is tested on random arrays and on real text: Joyce's *Ulysses*, the Da Vinci notebooks, and
another Gutenberg corpus. Random input hides the cases where a sort actually differs.

## Adversarial tests

Two tests attack implementations rather than verify them.

`AlgorithmicComplexityAttackTest` uses the `Aa`/`BB` hash collision to drive large numbers of keys into a
single `HashMap` bucket, and measures the degradation. Java 8 made this mostly harmless by switching
large buckets to balanced trees; the test documents where the boundary now is. It also checks the
`Math.abs(Integer.MIN_VALUE)` overflow that makes the naive way of computing a bucket index wrong.

`KillJavaSortTest` feeds McIlroy anti-quicksort inputs to `java.util.Arrays.sort` at 10K, 250K and 1M
elements, where it is expected to fail rather than sort. It is currently disabled and the generated input
files are not checked in.

## State of the code

Sorting, strings, graphs and union-find are covered by running tests. The symbol table corner is not:
`RedBlackBstTest` and `PatriciaTreeTest` are disabled stubs, so the left-leaning red-black BST and the
Patricia tree are implemented but untested. Four tests in `CharSeqMapTest` are disabled as well.

Documentation is thin, and the code does not always follow the conventions I prefer at work — curly
braces are sometimes omitted. `TODO.md` lists what is not here: mining, clustering, and social network
algorithms, mostly.

## Build

Java 21, Maven, JUnit 6.

```
mvn test
```
