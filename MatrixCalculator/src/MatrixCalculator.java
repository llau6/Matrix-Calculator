/**
 * Created by Lily on 11/15/2015.
 */
public class MatrixCalculator {

    /**
     * Approximates largest eigenvalue/corresponding eigenvector using the power method
     * @param a An nxn matrix
     * @param v Initial approximation vector
     * @param tol Tolerance error
     * @param n Max number of iterations
     * @return Returns a PowerObject that holds resulting eigenvalue, eigenvector, and iterations
     */
    public static PowerObject power_method(Matrix a, Vector v, double tol, int n) {
        return PowerMethod.power_method(a, v, tol, n);
    }

    /*public static Matrix[] lu_fact(Matrix a) {
        return LuFactorization.LuFactorization(a);
    }*/

    /**
     * Multiplies a matrix by a vector
     *
     * @param a The matrix
     * @param x The vector
     * @return The vector containing the product
     * @throws IllegalArgumentException if the given matrix's and vector's dimensions cannot be multiplied
     */
    public static Vector multiply(Matrix a, Vector x) {
        if (a.getMatrixColumns() != x.getVectorRows()) {
            throw new IllegalArgumentException("A's columns must be the same as B's rows");
        }
        Vector product = new Vector(a.getMatrixRows());
        double sum;
        double[] row;
        for (int rowA = 0; rowA < a.getMatrixRows(); rowA++) {
            sum = 0;
            row = a.getMatrixRow(rowA);
            for (int i = 0; i < row.length; i++) {
                sum += row[i] * x.getVectorEntry(i);
            }
            product.setVectorEntry(rowA, sum);
        }
        return product;
    }

    /**
     * Multiplies two matrices
     * @param a The first matrix
     * @param b The second matrix
     * @return The matrix containing the product
     * @throws IllegalArgumentException if the given
     *         matrices' dimensions cannot be multiplied
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getMatrixColumns() != b.getMatrixRows()) {
            throw new IllegalArgumentException("A's columns must be the same as B's rows");
        }
        Matrix product = new Matrix(a.getMatrixRows(), b.getMatrixColumns());
        double sum;
        for (int rowA = 0; rowA < a.getMatrixRows(); rowA++) {
            for (int colB = 0; colB < b.getMatrixColumns(); colB++) {
                sum = 0;
                for (int i = 0; i < a.getMatrixColumns(); i++) {
                    sum += a.getMatrixEntry(rowA, i) * b.getMatrixEntry(i, colB);
                }
                product.setMatrixEntry(rowA, colB, sum);
            }
        }
        return product;
    }

    /**
     * Multiplies a vector by a scalar
     * @param a The vector
     * @param scalar The constant to multiply by
     * @return The vector containing the product
     */
    public static Vector multiply(Vector a, double scalar) {
        Vector product = new Vector(a.getVectorRows());
        for (int i = 0; i < a.getVectorRows(); i++) {
            product.setVectorEntry(i, scalar * a.getVectorEntry(i));
        }
        return product;
    }

    /**
     * Adds two matrices' values together
     * @param a The first matrix
     * @param b The second matrix
     * @return The matrix of containing the sum
     * @throws IllegalArgumentException if the given
     *		   matrices have different dimensions
     */
    public static Matrix add(Matrix a, Matrix b) {

        if (a.getMatrixRows() != b.getMatrixRows() || a.getMatrixColumns() != b.getMatrixRows()) {
            throw new IllegalArgumentException("Rows and columns are not equal");
        }

        Matrix sum = new Matrix(a.getMatrixRows(), a.getMatrixColumns());
        double sumValue;
        for (int row = 0; row < a.getMatrixRows(); row++) {
            for (int column = 0; column < a.getMatrixColumns(); column++) {
                sumValue = a.getMatrixEntry(row, column) + b.getMatrixEntry(row, column);
                sum.setMatrixEntry(row, column, sumValue);
            }
        }
        return sum;
    }

    /**
     * Subtracts two matrices' values
     * @param a The first matrix
     * @param b The second matrix
     * @return The matrix of containing the difference
     * @throws IllegalArgumentException if the given
     *		   matrices have different dimensions
     */
    public static Matrix subtract(Matrix a, Matrix b) {

        if (a.getMatrixRows() != b.getMatrixRows() || a.getMatrixColumns() != b.getMatrixColumns()) {
            throw new IllegalArgumentException("Rows and columns are not equal");
        }

        Matrix difference = new Matrix(a.getMatrixRows(), a.getMatrixColumns());
        double differenceValue;
        for (int row = 0; row < a.getMatrixRows(); row++) {
            for (int column = 0; column < a.getMatrixColumns(); column++) {
                differenceValue = a.getMatrixEntry(row, column) - b.getMatrixEntry(row, column);
                difference.setMatrixEntry(row, column, differenceValue);
            }
        }
        return difference;
    }

    /**
     * Adds 2 vectors together.
     * @param u Vector to add
     * @param v Vector to add
     * @return resulting vector of adding u and v together
     */
    public static Vector add(Vector u, Vector v) {
        Vector sum = new Vector(u.getVectorRows());
        double sumValue;
        for (int row = 0; row < u.getVectorRows(); row++) {
            sumValue = u.getVectorEntry(row) + v.getVectorEntry(row);
            sum.setVectorEntry(row, sumValue);
        }
        return sum;
    }

    /**
     * Multiplies a vector by a matrix.
     * @param u The vector
     * @param uTranspose The matrix
     * @return The matrix containing the product between a vector and matrix
     */
    public static Matrix multiply(Vector u, Matrix uTranspose) {
        Matrix vector = new Matrix(u);
        return multiply(vector, uTranspose);

    }

    /**
     * Multiplies a matrix by a scalar
     * @param a The matrix
     * @param scalar The constant to multiply by
     * @return The matrix containing the product
     */
    public static Matrix multiply(Matrix a, double scalar) {
        Matrix product = new Matrix(a.getMatrixRows(), a.getMatrixColumns());
        for (int i = 0; i < a.getMatrixRows(); i++) {
            for (int j = 0; j < a.getMatrixColumns(); j++) {
                product.setMatrixEntry(i, j, scalar * a.getMatrixEntry(i, j));
            }
        }
        return product;
    }
}
