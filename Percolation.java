import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private byte[][] grid;
    private int N;
    private boolean isPercolated;
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        this.N = N;
        uf = new WeightedQuickUnionUF(N*N + 1);
        grid = new byte[N + 2][N + 2];
        for (int i = 0; i <= N; i++)
            for (int j = 1; j <= N; j++)
                grid[i][j] = 0;
    }

    public void open(int i, int j) {
        this.validate(i, j);
        if (getStatus(xyTo1D(i, j)) == 0) {
            if (i == 1) grid[i][j] = 3;
            else if (i == N) grid[i][j] = 5;
            else grid[i][j] = 1;
            byte status = grid[i][j];
            if (grid[i - 1][j] % 2 != 0) {
                status |= getStatus(uf.find(xyTo1D(i - 1, j)));
                uf.union(xyTo1D(i - 1, j), xyTo1D(i, j));
            }
            if (grid[i][j + 1] % 2 != 0) {
                status |= getStatus(uf.find(xyTo1D(i, j + 1)));
                uf.union(xyTo1D(i, j + 1), xyTo1D(i, j));
            }
            if (grid[i + 1][j] % 2 != 0) {
                status |= getStatus(uf.find(xyTo1D(i + 1, j)));
                uf.union(xyTo1D(i + 1, j), xyTo1D(i, j));
            }
            if (grid[i][j - 1] % 2 != 0) {
                status |= getStatus(uf.find(xyTo1D(i, j - 1)));
                uf.union(xyTo1D(i, j - 1), xyTo1D(i, j));
            }
            int p = uf.find(xyTo1D(i, j));
            if (p % N != 0) grid[p / N + 1][p % N] = status;
            else grid[p / N][N] = status;
            if (status == 7 || N == 1) isPercolated = true;
        }
    }

    public boolean isOpen(int i, int j) {
        this.validate(i, j);
        int p = uf.find(xyTo1D(i, j));
        return getStatus(p) % 2 != 0;
    }

    public boolean isFull(int i, int j) {
        this.validate(i, j);
        int p = uf.find(xyTo1D(i, j));
        return (getStatus(p) == 3 || getStatus(p) == 7);
    }

    public boolean percolates() {
        return isPercolated;
    }

    public static void main(String[] args) {
    }

    private byte getStatus(int x) {
        if (x % N != 0) return grid[x/N + 1][x % N];
        else return grid[x/N][N];
    }

    private int xyTo1D(int i, int j) {
        return j + (i - 1)*N;
    }

    private void validate(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("row index i out of bounds");
    }
}
