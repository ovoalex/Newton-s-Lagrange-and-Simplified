public class Polynomial {

    private double[] coefficients;  // Array of coefficients of the polynomial
    private int degree;             // Degree of the polynomial

    /**
     * Constructor that accepts an array of ordered coefficients, where the index corresponds to x^index.
     * @param coefficients
     */
    public Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        for(int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }
        findDegree();
    }

    /**
     * Constructor that forms the polynomial as ax^b, where a is the coefficients and b is the degree.
     * @param coefficients
     * @param degree
     */
    public Polynomial(Double coefficients, Integer degree) {
        this.coefficients = new double[(degree + 1)];
        this.coefficients[degree] = coefficients;
        this.degree = degree;
    }

    /**
     * Constructor that forms first degree polynomials as (ax - b)
     */
    public Polynomial(Double a, Double b) {
        this.coefficients = new double[2];
        this.coefficients[0] = (-1 * b);
        this.coefficients[1] = a;
        this.degree = 1;
    }


     //Find the degree of the polynomial.
    private void findDegree() {
        for (int i = (this.coefficients.length - 1); i >= 0; i--) {
            if(this.coefficients[i] != 0) {
                this.degree = i;
                break;
            }
        }
    }

    //Add poly to given polynomial
    
    public Polynomial add(Polynomial poly) {
        double[] otherCoefficients = poly.getCoefficients();
        double[] addedValues;

        // Store coefficients
        if (this.degree >= poly.getDegree())
            addedValues = new double[(this.degree + 1)];
        else
            addedValues = new double[(poly.getDegree() + 1)];

        // Add other coefficients to addedValues array
        for (int i = poly.getDegree(); i >= 0; i--) {
            addedValues[i] += otherCoefficients[i];
        }

        // Add this polynomials's coefficients values
        for (int i = this.degree; i >= 0; i--) {
            addedValues[i] += this.coefficients[i];
        }
        return (new Polynomial(addedValues));
    }
    
    //Multiply poly with the given polynomial.
    public Polynomial multiply(Polynomial poly) {
        // Degree of new polynomial will be addition of degrees of the two polynomials being multiplied
        double[] coefficients = new double[(this.degree + poly.getDegree() + 1)]; 
        double[] otherCoefficients = poly.getCoefficients();

        // Add degrees
        for (int i = 0; i <= this.degree; i++) {
            for (int j = 0; j <= poly.getDegree(); j++) {
                coefficients[i+j] += (otherCoefficients[j] * this.coefficients[i]);
            }
        }
        return (new Polynomial(coefficients));
    }

    // Multiply the given polynomial by a constant value.
    public Polynomial multiplyByConstant(double constant) {
        double[] coefficients = new double[(this.degree + 1)];

        // Degree of new polynomial will be addition of degrees of the two polynomials being multiplied
        for (int i = 0; i <= this.degree; i++) {
            coefficients[i] = (constant * this.coefficients[i]);
        }
        return (new Polynomial(coefficients));
    }

    //Print the polynomial 
    public String print() {
        StringBuilder poly = new StringBuilder();

        if (this.degree == 0)
            poly.append(String.format("%.3f", this.coefficients[0]));
        else if (this.degree == 1) {
            poly.append(String.format("%.3f", this.coefficients[1])).append("x");

            if (this.coefficients[0] > 0)
                poly.append(" + ").append(String.format("%.3f", this.coefficients[0]));
            else if (this.coefficients[0] < 0)
                poly.append(" - ").append(String.format("%.3f", Math.abs(this.coefficients[0])));
        }
        else {
            // Get first term
            poly.append(String.format("%.3f", this.coefficients[this.degree])).append("x^").append(this.degree);

            // For degrees > 1
            for (int i = degree - 1; i >= 0; i--) {
                // Get next coefficients
                if (this.coefficients[i] > 0)
                    poly.append(" + ").append(String.format("%.3f", this.coefficients[i]));
                else if (this.coefficients[i] < 0)
                    poly.append(" - ").append(String.format("%.3f", Math.abs(this.coefficients[i])));
                else
                    continue; // Skip if coefficients = 0

                // If degree > 1, add exponent to the x value
                if (i > 1)
                    poly.append("x^").append(i);
                else if (i == 1)
                    poly.append("x");
            }
        }
        return poly.toString();
    }

    /**
     * Get array of coefficients.
     * @return Current coefficients
     */
    public double[] getCoefficients() { 
    	return this.coefficients; }

    /**
     * Get degree.
     * @return Current degree
     */
    public int getDegree() { 
    	return this.degree; }
}