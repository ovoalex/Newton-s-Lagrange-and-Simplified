import java.io.*;
import java.util.*;

public class Main {

    /**
     *
     * Ask user to enter input file name.
     * @return File Input file
     */
    public static File getFileName() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Enter input file name: ");
            String fileName = scan.nextLine();
            File file = new File(fileName);

            // Return file if it exists
            if (file.exists())
                return file;
            // Print error message if file cannot be found
            System.out.println(fileName + " is invalid.\n");
        }
    }

    /**
     * Load data from input file.
     * @param file Input file
     * @return Data table with input file's numbers
     */
    public static double[][] loadFromFile(File file) {
        double[][] table = new double[0][0];
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(file.getName());
            BufferedReader buffer = new BufferedReader(fileReader);

            // File should have 2 lines
            // First line is x values, Second line is y values
            for (int i = 0; i < 2; i++) {
                String line = buffer.readLine();

                if (line != null) {
                    line = line.trim();

                    // Split string by space
                    String[] values = line.split("\\s+");
                    for (String value : values) {
                        try {
                            if (i == 0)  // First line x values
                                x.add(Double.parseDouble(value));
                            else  // Second line y values
                                y.add(Double.parseDouble(value));
                        }
                        catch (Exception e) {
                            System.out.println("Unable to parse " + value + " from file.");
                        }
                    }
                }
                else
                    System.out.println("Error reading input file.");
            }
            buffer.close();
            fileReader.close();

            // Put x and y values in double[][] table to return
            if (x.size() == y.size()) {
                table = new double[x.size()][2];
                for (int i = 0; i < x.size(); i++) {
                    table[i][0] = x.get(i);
                    table[i][1] = y.get(i);
                }
            }
            else
                System.out.println("Error reading input file.");
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return table;
    }

    public static void main(String[] args) {
        File inputFile = args.length != 0 ? new File(args[0]) : getFileName();
        double[][] table = loadFromFile(inputFile);
        double[][] ddTable = DividedDifferences.solveDD(table);

        System.out.println("\n--------------------------------------------------------------------------------------------------\n");

        // Print divided differences table
        System.out.println("Divided Differences Table:\n");
        DividedDifferences.formatPrintDDTable(ddTable);

        System.out.println("\n--------------------------------------------------------------------------------------------------\n");

        // Print polynomials in Newton's form
        NewtonForm.printUnsimplifiedNewton(ddTable);
        System.out.println("\nNewton's Simplified Polynomial:");
        System.out.println(NewtonForm.getSimplifiedNewton(ddTable).print());

        System.out.println("\n--------------------------------------------------------------------------------------------------\n");

        // Print polynomials in Lagrange's form
        System.out.println("Lagrange's Unsimplified Polynomial:");
        System.out.println(LagrangeForm.printUnsimplifiedLagrange(table));
        System.out.println("\nLagrange's Simplified Polynomial:");
        System.out.println(LagrangeForm.getSimplifiedLagrange(table).print());

        System.out.println("\n--------------------------------------------------------------------------------------------------\n");
    }
}