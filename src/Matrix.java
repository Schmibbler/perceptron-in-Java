import java.util.Random;
public class Matrix implements Except {
    protected double [][] array;
    protected double [][] T;
    protected int rows;
    protected int cols;
    public Random rand;


    public Matrix (int rows, int cols) {
        rand = new Random();
        if (rows <= 0 || cols <= 0) InvalidDimensions("Cannot initialize matrix with rows or columns of length 0");
        this.rows = rows;
        this.cols = cols;
        this.array = new double[rows][cols];
        this.T = new double[cols][rows];
    }

    public Matrix (double arr[][]) {
        rand = new Random();
        this.rows = arr.length;
        this.cols = arr[0].length;
        this.T = null;
    }
    public Matrix () {
        rand = new Random();
        this.array = null;
        this.T = null;
        this.rows = 0;
        this.cols = 0;
    }
    /* Use this later */

    protected boolean element_wiseable(Matrix m) {
        Matrix m1 = this;
        Matrix m2 = m;
        return ((m1.rows == m2.rows && m1.cols == m2.cols) || (m1.rows == m2.cols && m1.cols == m1.rows) || (m1.cols == m2.rows && m1.rows == m2.cols));
    }

    // Randomize the values of the matrices within the left and right bounds
    public void randomize(double left, double right) {
        try {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++) {
                    array[i][j] = rand_in_range(left, right);
                    T[j][i] = array[i][j];
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Randomize the values of the matrices within default bounds [-1, 1]
    public void randomize() {
        try {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++) {
                    array[i][j] = rand_in_range(-1, 1);
                    T[j][i] = array[i][j];
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected double rand_in_range(double low, double high) {
        return (int) ((Math.random() * (high - low)) + low);
    }
    void display() {
        try {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++)
                    System.out.printf("[%.4f] ", this.array[i][j]);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Unable to display matrix");
            e.printStackTrace();
        }
    }
    public Matrix dot(Matrix m) {
        Matrix m1 = this;
        Matrix m2 = m;

        if (m1.cols != m2.rows) {
            String reason = "Cannot multiply matrices of mismatching dimensions " + m1.dimensions() + "x " + m2.dimensions();
            MismatchingDimensions(reason);
        }

        Matrix result = new Matrix (m1.rows, m2.cols);
        for (int i = 0; i < m1.rows; i++)
            for (int j = 0; j < m2.cols; j++)
                for (int k = 0; k < m1.cols; k++)
                    result.array[i][j] += m1.array[i][k] * m2.array[k][j];

        return result;
    }

    public Matrix dot(double arr[][]) {
        Matrix m2 = new Matrix(arr);
        return this.dot(m2);
    }

    public String dimensions() {
        return "( " + this.rows + ", " + this.cols + " ) ";
    }

    public void multiply_by(double val) {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.array[i][j] *= val;
    }

    public void subtract(Matrix m) {
        if (!this.element_wiseable(m))
            NotElementWiseable("Cannot perform element-wise operations on matrices of dimensions: " + this.dimensions() + "vs " + m.dimensions());
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.array[i][j] -= m.array[i][j];
    }
    public void multiply(Matrix m) {
        if (!this.element_wiseable(m))
            NotElementWiseable("Cannot perform element-wise operations on matrices of dimensions: " + this.dimensions() + "vs " + m.dimensions());
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.array[i][j] *= m.array[i][j];
    }

    // The "exceptions" go here
    public void InvalidDimensions(String reason) {
        System.out.println(reason);
        System.exit(1);
    }
    public void MismatchingDimensions(String reason) {
        System.out.println(reason);
        System.exit(1);
    }
    public void NotElementWiseable(String reason) {
        System.out.println(reason);
        System.exit(1);
    }
}
