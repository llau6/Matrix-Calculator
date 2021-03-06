//import jxl.Workbook;
//import jxl.write.Number;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import jxl.write.WriteException;

import java.util.Random;
import java.io.*;

/**
 * Generates 100 vectors as initial vector guesses.
 * Calls the Jacobi and Gauss iteration methods on them.
 * Write the number of iterations and erro to a .txt and .xsl file
 *
 * @author Bruck Woldeyes
 */
public class jacobi_gauss_graph {
    //method to generate 100 x's
    //method to generate 100 x's
    public static Vector generateXk() {
        Random rand = new Random();
        double min = -1;
        double max = 1;
        int i = 0;
        Vector vec = new Vector(3);
        for(int j = 0; j < 3; j++) {
            vec.setVectorEntry(j,min + (max - min) * rand.nextDouble());
        }
        return vec;
    }

    public static void writeGraphs(int iterations) {
        int count = 0;
        double tol = .00005;
        File file = new File("Jacobi_Gauss_Data.txt");

        double[] vecE = {(double)9/190, (double)28/475, (double) 33/475};
        Vector exact = new Vector(vecE);
        //approximation for Jacobi
        Vector approxJ = new Vector(3);
        //approximation fo gauss
        Vector approxG = new Vector(3);
        // Number of jacobi iterartions
        double numJIterations = 0;
        //Number of Gauss iterations
        int numGIterations = 0;
        while (count < iterations) {
            Vector xk = generateXk();
            //Vector.computeError(xk, exact);
            System.out.print("Xk: ");
            System.out.println(xk);
            double error = Vector.computeError(xk, exact);
            System.out.println("Initial error: " + error);
            //solution using Jacobi

            System.out.println("Jacobi Solution: ");
            Vector JacobiXn = generateJacobi(xk);
            //numJIterations = numJIterations + Jacobi.getIterations();
            System.out.println(JacobiXn);
            approxJ = MatrixCalculator.add(approxJ, JacobiXn);

            //gauss stuff
            System.out.println("");
            System.out.println("gauss_seidel Solution: ");
            Vector gaussXn = generateGauss(xk);
            numGIterations = numGIterations + gauss_seidel.getIterations();
            System.out.println(gaussXn);

            approxG = MatrixCalculator.add(approxG, gaussXn);
            System.out.println("");
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                //initial error
                //x-axis: ||Xo-Xexact||
                bw.write(error + "\n");
                //gauss iterations
                bw.write(Jacobi.getIterations()  + "\n");
                //jacobi iterations
                bw.write(gauss_seidel.getIterations() + "\n");
                bw.close();
            } catch (IOException e) {
                System.out.println("Could not write.");
            }
            count++;
        }
        //approximate solution from adding up all of the Xn's
        MatrixCalculator.divide(approxJ, 100.0);
        System.out.print("Approximate Solution using Jacobi: ");
        System.out.println(approxJ);
        System.out.println("error: " + Vector.computeError(approxJ, exact));
        System.out.println("");

        System.out.print("Approximate Solution using Gauss: ");
        MatrixCalculator.divide(approxG, 100.0);
        System.out.println(approxG);
        System.out.println("error: " + Vector.computeError(approxG, exact));
        System.out.print("");
        System.out.println("Iteration ratio: " + (double)numJIterations/numGIterations);
    }

    public static Vector generateJacobi (Vector x) {
        double [][] matrix = new double[][]{
                {1, .5, .33},
                {.5, 1, .5},
                {.33, .25, 1}
        };
        // the matrix we will be working with
        Matrix A = new Matrix(matrix);
        // the b vector
        final double[] vecb = {0.1,0.1,0.1};
        final Vector b = new Vector(vecb);
        return Jacobi.jacobi_iter(x,.00005,100);
    }

    public static Vector generateGauss(Vector x) {
        double [][] matrix = new double[][]{
                {1, .5, .33},
                {.5, 1, .5},
                {.33, .25, 1}
        };
        // the matrix we will be working with
        Matrix A = new Matrix(matrix);
        // the b vector
        final double[] vecb = {0.1,0.1,0.1};
        final Vector b = new Vector(vecb);
        return gauss_seidel.gs_iter(x,.00005,100);
    }
//    public static void writeExcel() {
//        int count = 0;
//        int cols = 3;
//        try {
//            WritableWorkbook workbook = Workbook.createWorkbook(new File("JacobiGaussExcel.xls"));
//            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
//            try (BufferedReader br = new BufferedReader(new FileReader(new File("Jacobi_Gauss_Data.txt")))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    try {
//                        if (count % cols == 0) {
//                            sheet.addCell(new Number(0, count/cols, Double.parseDouble(line)));
//                        } else if (count % cols == 1) {
//                            sheet.addCell(new Number(1, count/cols, Double.parseDouble(line)));
//                        } else if (count % cols == 2) {
//                            sheet.addCell(new Number(2, count/cols, Double.parseDouble(line)));
//                        }
//                        count++;
//                    } catch (jxl.write.WriteException e) {
//                        System.out.println("Cannot write.");
//                    }
//
//                }
//            }
//            workbook.write();
//            workbook.close();
//        } catch (IOException e) {
//            System.out.println("op");
//        } catch (WriteException e) {
//            e.printStackTrace();
//        }
//    }
}

