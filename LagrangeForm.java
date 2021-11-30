public class LagrangeForm {

    /**
     * Print the unsimplified polynomial in Lagrange's form.
     * @param table Data table
     * @return The unsimplified polynomial
     */
    public static String printUnsimplifiedLagrange(double[][] table) {
        StringBuilder output = new StringBuilder();
        StringBuilder values;

        for(int i = 0; i < table.length; i++) {
            values = new StringBuilder();
            // Get the functional value to multiply by
            if (i == 0)
                values.append(String.format("%.2f", table[i][1])).append(" * (");
            else
                values.append(String.format("%.2f", Math.abs(table[i][1]))).append(" * (");

            // Get numerators
            for (int j = 0; j < table.length; j++) {
                if (i != j) {
                    values.append("(x");
                    if (table[j][0] > 0)
                        values.append(" - ").append(String.format("%.1f", table[j][0])).append(")");
                    else if (table[j][0] < 0)
                        values.append(" + ").append(String.format("%.1f", Math.abs(table[j][0]))).append(")");
                    else
                        values.append(")"); // If value is 0, close parenthesis
                }
            }

            // Divide symbol
            values.append(" / ");

            // Get denominators
            for (int j = 0; j < table.length; j++) {
                if (i != j) {
                    values.append("(").append(String.format("%.1f", table[i][0]));
                    if (table[j][0] > 0)
                        values.append(" - ").append(String.format("%.1f", table[j][0])).append(")");
                    else if (table[j][0] < 0)
                        values.append(" + ").append(String.format("%.1f", Math.abs(table[j][0]))).append(")");
                    else
                        values.append(")");
                }
            }
            values.append(")");
            output.append(values.toString());

            // Print + or - sign if we are NOT at the end of the polynomial
            if ((i+1) != table.length) {
                if (table[(i+1)][1] >= 0)
                    output.append("\n+ ");
                else
                    output.append("\n- ");
            }
        }
        return output.toString();
    }

    /**
     * Get the simplified polynomial in Lagrange's form.
     * @param table Data table
     * @return The simplified polynomial
     */
    public static Polynomial getSimplifiedLagrange(double[][] table) {
        Polynomial simplified = null;

        for (int i = 0; i < table.length; i++) {
            Polynomial lagrange = null;
            // Set f(xi) as initial multiplier
            double multiplier = table[i][1];

            // Numerator is the actual polynomial value that will later be multiplied by functional value and denominator
            for (int j = 0; j < table.length; j++) {
                if (i != j) {
                    if (lagrange == null)
                        lagrange = new Polynomial(1.0, table[j][0]);
                    else {
                        Polynomial nextTerm = new Polynomial(1.0, table[j][0]);
                        lagrange = lagrange.multiply(nextTerm);
                    }

                    // Multiplier for polynomial is denominator * functional value
                    multiplier = multiplier * (1.0 / (table[i][0] - table[j][0]));
                }
            }
            lagrange = lagrange.multiplyByConstant(multiplier);
            // First term
            if (simplified == null)
                simplified = new Polynomial(lagrange.getCoefficients());
            else
                simplified = simplified.add(lagrange);
        }
        return simplified;
    }
}