
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF container;
    private final WeightedQuickUnionUF backwash;
    private boolean [] isOpenFlag; // 0 for close; 1 for open
    private int count = 0;
    private final int size;

    public Percolation(int n) {
        size = n;
        validateCoordinate(n, n);
        container = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 2);
        isOpenFlag = new boolean [n * n + 2];
    }
    private void validateCoordinate(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Coordinate shoule be between 1 and " + size);
        }
    }
    
    private void union(int p, int q) {
        container.union(p, q);
        backwash.union(p, q);
    }
    
    // open the site (row, col) if it has not been opened 
    public void open(int row, int col) {
        validateCoordinate(row, col);
        int node = convertToNum(row, col);    
        if (!isOpen(row, col) && size != 1) {
            if (row == 1) {
                if (isOpen(row + 1, col)) {
                    union(node, node + size);
                }
                // connect to the virtual top node;
                union(size * size, node);
            } else if (row == size) {
                if (isOpen(row - 1, col)) {
                    union(node, node - size); 
                }
                // connect to the virtual bottom node
                backwash.union(size * size + 1, node);
            } else {
                if (isOpen(row - 1, col)) {
                    union(node, node - size); 
                }
                if (isOpen(row + 1, col)) {
                    union(node, node + size); 
                }
            }

            if (col == 1) {
                if (isOpen(row, col + 1)) {
                    union(node, node + 1); 
                }
            } else if (col == size) {
                if (isOpen(row, col - 1)) {
                    union(node, node - 1); 
                }
            } else {
                if (isOpen(row, col + 1)) {
                    union(node, node + 1); 
                }
                if (isOpen(row, col - 1)) {
                    union(node, node - 1); 
                }
            }
            count += 1;
            isOpenFlag[node] = true;
        } else if (!isOpen(row, col) && size == 1) {
            isOpenFlag[node] = true;
            union(size * size, node);
            backwash.union(size * size + 1, node);
        }
            
    }

    private int convertToNum(int row, int col) {
        return (row - 1) * size + col -1;
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateCoordinate(row, col);
        return isOpenFlag[convertToNum(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateCoordinate(row, col);
        int root = container.find(convertToNum(row, col));
        int topRoot = container.find(size*size);
        return (root == topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return count; }

    // does the system percolate?
    public boolean percolates() { return backwash.find(size * size) == backwash.find(size * size + 1); }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation test can be written here.
    }
}
