# alg-java

Algorithms and data structures implemented from scratch in Java. I wrote this to refresh and deepen
knowledge, and to have a place where I can try design ideas without production constraints. It is not a
library and I do not recommend depending on it. `java.util` is faster and better tested.

85 implementations covering about 58 distinct algorithms and data structures.

## Contents

| Area | Implementations | What is in there |
|---|---|---|
| Sorting | 33 | insertion, selection, bubble, shaker, shell, heap, merge, optimal merge, quick (recursive and explicit stack), three-way quick, quickselect, counting, bucket, LSD/MSD/straight radix, inversion counting |
| Graphs | 16 | BFS, DFS (recursive and explicit stack), Dijkstra, Bellman-Ford, DAG shortest paths, connected components, strongly connected components, topological sort, Ford-Fulkerson max flow, Kruskal, Prim (lazy and eager), scheduling |
| Symbol tables and trees | 6 | left-leaning red-black BST, R-way trie, ternary search tree, Fenwick tree, linear probing and separate chaining hash maps |
| Strings | 7 | Knuth-Morris-Pratt, Rabin-Karp Las Vegas (plain and incremental), brute force with and without explicit backup, Levenshtein, longest repeated substring |
| Heaps and queues | 5 | binary min/max heap, indexed min/max heap, array queue |
| Union-find | 4 | quick-find, quick-union, weighted quick-union, weighted quick-union with path compression |
| Math | 8 | six sequential Fibonacci strategies plus a ForkJoin one, fast exponentiation |
| Search | 3 | binary search over arrays, ints, and inverted arrays |
| Compression, geometry | 2 | Huffman coding, Graham scan |
| Data streams | 1 | Bloom filter (two independent hash functions simulating k via Kirsch-Mitzenmacher) |

The implementation count is higher than the algorithm count on purpose. Several algorithms exist in more
than one form: a generic version and an `int`-specialized one, or a sequential version and a ForkJoin
parallel one. They are meant to be compared against each other, not to be deduplicated.

The counts include only working algorithm implementations — interfaces, node classes, graph and edge
data types, ForkJoin task classes, facades and utilities are not counted, and neither are the two
unfinished sketches (`PatriciaTree`, `SuffixTree`, see below).
Excludes the unfinished sketches; known defects in individual operations are listed under State of the code.

## How it is organized

Every family sits behind a strategy interface: `SortStrategy`, `IntSortStrategy`, `RadixSortStrategy`,
`MstStrategy`, `CompressionStrategy`, `StringSearch`, `UnionFind`, `TopologicalSort`, `SymbolTable`.
Implementations are interchangeable, and the tests are written against the interface rather than against
the implementation.

Consequently the test suites are conformance suites. `AbstractSortTest` and `AbstractIntSortTest` define
the behaviour every sort must satisfy, time each run, and are subclassed once per implementation.
`SystemSortTest` and `IntSystemSortTest` put `java.util.Arrays.sort` through the same suite, so the JDK
is a baseline in the same harness as everything else. The symbol tables run through the same pattern:
`SymbolTableTest` and `CharSeqMapTest` are shared conformance suites subclassed per data structure, and
they check against `java.util` models (`HashSet`, `TreeSet`) instead of hard-coded expectations.

Sorting is tested on random arrays and on real text: Joyce's *Ulysses*, the Da Vinci notebooks, and
another Gutenberg corpus. Random input hides the cases where a sort actually differs.

## Adversarial tests

Two tests attack implementations rather than verify them.

`AlgorithmicComplexityAttackTest` uses the `Aa`/`BB` hash collision to drive large numbers of keys into a
single `HashMap` bucket, and measures the degradation. Java 8 made this mostly harmless by switching
large buckets to balanced trees; the test documents where the boundary now is. It also checks the
`Math.abs(Integer.MIN_VALUE)` overflow that makes the naive way of computing a bucket index wrong.

`KillJavaSortTest` is meant to attack `java.util.Arrays.sort` with McIlroy's killer adversary (M. D.
McIlroy, *A Killer Adversary for Quicksort*, 1999), but is currently disabled: the input files it
expects (`antiquicksort10K.txt`, `antiquicksort250K.txt`, `antiquicksort1M.txt`) are not checked in
and the assertion is commented out. See `TODO.md`.

## State of the code

Sorting, strings, graphs, union-find, compression and the bitwise stream helpers are covered by running
tests. The symbol tables run through the shared conformance suites, and `RedBlackBstTest` additionally
verifies the red-black invariants — no right-leaning red links, no two consecutive reds, perfect black
balance, BST order — after randomised and sorted insertion sequences.

Running the previously disabled suites exposed real defects, which are documented rather than hidden:
every remaining `@Disabled` test states its reason and has a matching entry in `TODO.md`. The known ones
are: `LLRedBlackBst.delete` destroys the tree (its `fixUp` helper is an unimplemented stub);
`TernaryTree`'s `delete`, `min` and `max` are buggy and its `floor`/`ceiling` unfinished; `RWayTrie`'s
ordered operations (`min`/`max`/`floor`/`ceiling`) are stubs; and `PatriciaTree` and `SuffixTree` are
unfinished sketches, not working data structures.

Documentation is thin, and the code does not always follow the conventions I prefer at work — curly
braces are sometimes omitted. `TODO.md` lists what is not here: mining, clustering, and social network
algorithms, mostly.

## License

Apache-2.0, see `LICENSE`. The repository contains no third-party code: the bitwise stream helpers
originally taken from the algs4 booksite were replaced by an independent reimplementation written
against a specification (`docs/binary-streams.md`) and conformance tests that were written first.

## Build

Java 21, Maven, JUnit 6.

```
mvn test
```
