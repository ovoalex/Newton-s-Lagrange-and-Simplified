public class DividedDifferences {

    private static final int MIN_WIDTH = 8;
    
    /**
     * Use divided differences method to solve for the interpolating polynomial.
     * @param printTable Data table
     * @return
     */
    public static double[][] solveDD(double[][] printTable){
        double[][] ddTable = new double[printTable.length][printTable.length + 1];

        // Copy values from printTable into ddTable
        for (int i = 0; i < printTable.length; i++) {
            ddTable[i][0] = printTable[i][0];
            ddTable[i][1] = printTable[i][1];
        }

        // Solve for each f[ ] column
        for (int j = 2; j < ddTable[0].length; j++) {
            for (int i = 0; i < (ddTable[0].length - j); i++) {
                ddTable[i][j] = (ddTable[i+1][j-1] - ddTable[i][j-1]) / (ddTable[i+(j-1)][0] - ddTable[i][0]);
            }
        }
        return ddTable;
    }

    /**
     * Format and initialize a String[][] printTable for printing ddTable
     * @param ddTable Divided differences printTable
     */
    public static void formatPrintDDTable(double[][] ddTable) {
        String[][] printTable = new String[(ddTable.length * 2)][ddTable[0].length];

        // Initialize empty strings wherever needed
        int width = MIN_WIDTH;
        if (printTable[0].length > MIN_WIDTH)
            width = printTable[0].length + 3; // add 3 buffer space

        // Format strings and space
        for (int i = 0; i < printTable.length; i++) {
            for (int j = 0; j < printTable[0].length; j++) {
                printTable[i][j] = String.format(("| %" + width + "s"), " ");
            }
        }

        // Initialize first two columns with x and f[] values
        for (int i = 0; i < ddTable.length; i++) {
            printTable[(i*2)][0] = String.format("| %-" + width + ".3f", ddTable[i][0]);
            printTable[(i*2)][1] = String.format("| %-" + width + ".3f", ddTable[i][1]);
        }

        // Initialize the rest of the printTable's values
        for (int i = 2; i < ddTable[0].length; i++) {

            // As column value increases, rows needing values input decrease by factor of
            // (total # columns) - (current column #)

            // Starting row = current column # - 1
            int rowCount = (i - 1);
            for (int j = 0; j < (ddTable[0].length - i); j++) {
                printTable[rowCount][i] = String.format("| %-" + width + ".3f", ddTable[j][i]);
                rowCount = rowCount + 2;
            }
        }

        // Print the printTable
        printDDTable(printTable);
    }

    /**
     * Print the divided differences printTable
     * @param ddTable Divided differences printTable
     */
    public static void printDDTable(String[][] ddTable) {
        int width = MIN_WIDTH;
        if (ddTable[0].length > MIN_WIDTH)
            width = ddTable[0].length + 3; // +3 to add buffer space

        // Print x and f[] column headers
        System.out.print(String.format("| %-" + width + "s", "x"));
        System.out.print(String.format("| %-" + width + "s", "f[]"));

        // Print the rest of the f[ ] columns with the appropriate commas
        for(int i = 2; i < ddTable[0].length; i++) {
            StringBuilder label = new StringBuilder();
            label.append("f[");
            for (int j = 0; j <= (i - 2); j++) {
                label.append(",");
            }
            label.append("]");
            System.out.print(String.format("| %-" + width + "s", label.toString()));
        }
        System.out.println();

        // Output the rest of ddTable
        for (int i = 0; i < ddTable.length; i++) {
            for (int j = 0; j < ddTable[0].length; j++) {
                System.out.print(ddTable[i][j]);
            }
            System.out.println();
        }

    }

}