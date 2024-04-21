package coursera.programs;

public class Percolation {
  private final int[] id;
  private final int[] size;

  public Percolation(int n) {
    id = new int[n * n];
    size = new int[n * n];
    for ( int i = 0; i < id.length; i++ ) {
      id[i] = i;
      size[i] = 1;
    }
  }

  public void open(int i, int j) {

  }

  public boolean isOpen(int i, int j) {
    return root(i) == root(j);
  }

  public boolean isFull(int i, int j) {
    return false;
  }

  public boolean percolates() {
    return false;
  }

  private int root(int i) {
    return -1;
  }
}
