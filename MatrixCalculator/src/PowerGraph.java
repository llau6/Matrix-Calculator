import java.io.*;
import java.util.Random;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

/**
 * Created by Lily on 11/15/2015.
 */
public class PowerGraph {
    public static void writeGraphs(int iterations) {
        int count = 0;
        double tol = .00005;
        double[] varr = {1, 1};
        Vector v = new Vector(varr);
        File file = new File("graphA.txt");
        while (count < iterations) {
            Matrix a = generateMatrix();
            PowerObject powObj = MatrixCalculator.power_method(a, v, tol, 100);
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(computeDeterminant(a) + "\n");
                bw.write(computeTrace(a) + "\n");
                bw.write(powObj.getIterations() + "\n");
                bw.write(a.getMatrixEntry(0,0) + "\n");
                bw.write(a.getMatrixEntry(0,1) + "\n");
                bw.write(a.getMatrixEntry(1,0) + "\n");
                bw.write(a.getMatrixEntry(1,1) + "\n");
                bw.close();
            } catch (IOException e) {
                System.out.println("Could not write.");
            }
            count++;
        }
        writeExcel();
    }
    public static void writeExcel() {
        int count = 0;
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            try (BufferedReader br = new BufferedReader(new FileReader(new File("graphA.txt")))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        if (count % 7 == 0) {
                            sheet.addCell(new Number(0, count/7, Double.parseDouble(line)));
                        } else if (count % 7 == 1) {
                            sheet.addCell(new Number(1, count/7, Double.parseDouble(line)));
                        } else if (count % 7 == 2) {
                            sheet.addCell(new Number(2, count/7, Double.parseDouble(line)));
                        } else if (count % 7 == 3) {
                            sheet.addCell(new Number(3, count/7, Double.parseDouble(line)));
                        } else if (count % 7 == 4) {
                            sheet.addCell(new Number(4, count/7, Double.parseDouble(line)));
                        } else if (count % 7 == 5) {
                            sheet.addCell(new Number(5, count/7, Double.parseDouble(line)));
                        } else if (count % 7 == 6) {
                            sheet.addCell(new Number(6, count / 7, Double.parseDouble(line)));
                        }
                        count++;
                    } catch (jxl.write.WriteException e) {
                        System.out.println("Cannot write.");
                    }

                }
            }
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            System.out.println("op");
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a 2x2 matrix with entries between [-2, 2]
     * @return A 2x2 matrix
     */
    public static Matrix generateMatrix() {
        Random r = new Random();
        double min = -2;
        double max = 2;
        double[][] arr = new double [2][2];
        // Generate random number in [-2, 2] for each entry
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = min + (max - min) * r.nextDouble();
            }
        }
        Matrix a = new Matrix(arr);
        return a;
    }

    /**
     * Computes the inverse of a 2x2 matrix
     * @param a A 2x2 matrix A
     * @return The inverse of matrix A or null if the inverse does not exist
     */
    public static Matrix computeInverse(Matrix a) {
        Matrix inv = new Matrix(2, 2);
        double det = computeDeterminant(a);
        if (det == 0) {
            return null;
        } else {
            inv.setMatrixEntry(0, 0, det * a.getMatrixEntry(1, 1));
            inv.setMatrixEntry(1, 1, det * a.getMatrixEntry(0, 0));
            inv.setMatrixEntry(0, 1, det * -a.getMatrixEntry(0, 1));
            inv.setMatrixEntry(1, 0, det * -a.getMatrixEntry(1, 0));
            return inv;
        }
    }

    /**
     * Computes the determinant of a 2x2 matrix
     * @param a A 2x2 matrix A
     * @return The determinant of matrix A
     */
    public static double computeDeterminant(Matrix a) {
        return 1 / ((a.getMatrixEntry(0, 0) * a.getMatrixEntry(1, 1)) - (a.getMatrixEntry(0, 1) * a.getMatrixEntry(1, 0)));
    }

    /**
     * Computes the trace of a 2x2 matrix
     * @param a A 2x2 matrix A
     * @return The trace of matrix A
     */
    public static double computeTrace(Matrix a) {
        return a.getMatrixEntry(0, 0) + a.getMatrixEntry(1, 1);
    }
}
