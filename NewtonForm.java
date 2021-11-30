import java.util.*;

public class NewtonForm {

    /**
     * Print the interpolating polynomial.
     * @param ddTable Divided differences table
     */
    public static void printUnsimplifiedNewton(double[][] ddTable) {
        StringBuilder polynomial = new StringBuilder();

        // First term
        polynomial.append(String.format("%.3f", ddTable[0][1]));
        if (ddTable[0].length > 2) {
            if (ddTable[0][2] > 0)
                polynomial.append(" + ");
            else
                polynomial.append(" - ");
        }

        // The rest of the polynomial
        for (int i = 1; i < ddTable.length; i++) {
            polynomial.append(String.format("%.3f", Math.abs(ddTable[0][(i+1)])));
            for (int j = 0; j <= (i-1); j++) {
                polynomial.append("(x");
                if (ddTable[j][0] > 0)
                    polynomial.append(" - ");
                else if (ddTable[j][0] < 0)
                    polynomial.append(" + ");

                if (ddTable[j][0] != 0)
                    polynomial.append(Math.abs(ddTable[j][0]));

                polynomial.append(")");
            }

            // Print + or - sign if we are NOT at the end of the polynomial
            if ((i+1) != ddTable.length) {
                if (i > 3) {
                    if (ddTable[0][(i+2)] > 0)
                        polynomial.append("\n+ ");
                    else
                        polynomial.append("\n- ");
                }
                else {
                    if (ddTable[0][(i+2)] > 0)
                        polynomial.append(" + ");
                    else
                        polynomial.append(" - ");
                }
            }
        }
        System.out.println("Newton's Unsimplified Polynomial: ");
        System.out.println(polynomial.toString());
    }

    /**
     * Get the simplified interpolating polynomial.
     * @param ddTable Divided differences table
     * @return Simplified polynomial
     */
    public static Polynomial getSimplifiedNewton(double[][] ddTable) {
        // Each term is the next (x-a) multiple
        //   ex. p[0] is (x-a), p[1] is (x-a)(x-b), ...
        List<Polynomial> p = new ArrayList<>();

        double[] coefficients = new double[(ddTable[0].length - 1)];
        double[] xValues = new double[(ddTable.length)];

        // Get coefficients
        // Starting from second row of divided difference table holds coefficients
        for (int i = 1; i < ddTable[0].length; i++)
            coefficients[(i-1)] = ddTable[0][i];

        // Get x values
        // First column of divided difference table holds x values
        for (int i = 0; i < ddTable.length; i++)
            xValues[i] = ddTable[i][0];

        // Build initial polynomial using constructor which creates (x - a)
        Polynomial polyBuilder = new Polynomial(1.0, xValues[0]);
        p.add(new Polynomial(polyBuilder.getCoefficients()));

        // Form sub-polynomials by multiplying previous by (x - xValues[i])
        for (int i = 1; i < (xValues.length - 1); i++) {
            // The next term we are multiplying into polyBuilder
            Polynomial poly = new Polynomial(1.0, xValues[i]);
            polyBuilder = polyBuilder.multiply(poly);
            // Add new term to the list
            p.add(new Polynomial(polyBuilder.getCoefficients()));
        }

        // Build final term as a polynomial with only x^0 term
        Polynomial simplified = new Polynomial(coefficients[0], 0);
        for (int i = 1; i < coefficients.length; i++) {
            Polynomial term = p.get((i-1));
            term = term.multiplyByConstant(coefficients[i]);
            simplified = simplified.add(term);
        }
        return simplified;
    }
}