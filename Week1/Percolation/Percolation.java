
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF container;
    private boolean [] isOpenFlag; // 0 for close; 1 for open
    private int count = 0;
    private final int size;

    public Percolation(int n) {
        size = n;
        validate(n);
        container = new WeightedQuickUnionUF(n * n + 2);
        isOpenFlag = new boolean [n * n + 2];
    }
    private void validate(int p) {
        if (p <= 0 || p > size) {
            throw new IllegalArgumentException("index " + p + " is not between 1 and " + size);
        }
    }
    // opens the site (row, col) if it is not open already 
    public void open(int row, int col) {
        validate(row);
        validate(col);
        int node = convertToNum(row, col);    
        if (!isOpen(row, col)) {
            if (row == 1) {
                if (isOpen(row + 1, col)) {
                    container.union(node, node + size);
                    // connect the first layer together					
                }
                container.union(size * size, node);
            } else if (row == size) {
                if (isOpen(row - 1, col)) {
                    container.union(node, node - size);
                    // connect the last layer together					
                }
                container.union(size * size + 1, node);
            } else {
                if (isOpen(row - 1, col)) {
                    container.union(node, node - size);    					
                }
                if (isOpen(row + 1, col)) {
                    container.union(node, node + size);
                }
            }

            if (col == 1) {
                if (isOpen(row, col + 1)) {
                    container.union(node, node + 1);
                }
            } else if (col == size) {
                if (isOpen(row, col - 1)) {
                    container.union(node, node - 1);
                }
            } else {
                if (isOpen(row, col + 1)) {						
                    container.union(node, node + 1); 
                }
                if (isOpen(row, col - 1)) {
                    container.union(node, node - 1);
                }
            }
            count += 1;
        }
        isOpenFlag[node] = true;
        
    }

    private int convertToNum(int row, int col) {
        return (row - 1) * size + col -1;
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return isOpenFlag[convertToNum(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        int root = container.find(convertToNum(row, col));
        int topRoot = container.find(size*size);
        return (root == topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return count; }

    // does the system percolate?
    public boolean percolates() { return container.find(size * size) == container.find(size * size + 1); }

    // test client (optional)
    public static void main(String[] args) {
        //    	Percolation abc = new Percolation(3);
        //    	System.out.println(abc.isOpen(1, 1));// false
        //    	System.out.println(abc.isFull(1, 1));// false
        //    	abc.open(1, 1);
        //    	System.out.println(abc.isOpen(1, 1));// true
        //    	System.out.println(abc.isFull(1, 1));// true
        //    	abc.open(2, 1);
        //    	System.out.println(abc.isOpen(2, 1));// true
        //    	System.out.println(abc.isFull(2, 1));// true
        //    	System.out.println(abc.percolates());// false
        //    	abc.open(3, 1);
        //    	System.out.println(abc.percolates());// true
        //    	System.out.println(abc.numberOfOpenSites());
    }
}
