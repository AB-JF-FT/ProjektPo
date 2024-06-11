package Projekt;

import java.util.Random;

/**
 * This class represents a graph and contains methods for creating and processing graph data.
 *
 * @author Jakub Fabiński
 */
public class Graph {
    /**
     * Boolean flag for testing purposes.
     */
    boolean testing = true;
    
    // parameters
    double V, a, c, A, w, n;
    // simplification constants
    double lineC, expC;

    // tables
    double time[];
    double tLimit;

    double power[];
    double powerJ[];
    double powerU[];

    // Integral values
    static double integral;
    static double integralJ;
    static double integralU;

    // int used for getting the number of data used for graph
    static int L;

    /**
     * Imports parameters from text fields in the UI class.
     * Any side-effects: Modifies the instance variables V, a, c, A, w, n, lineC, and expC.
     * 
     * @param Ui The UI class instance.
     */
    protected void getParameters() {
        V = Double.parseDouble(Ui.textField1.getText());
        a = Double.parseDouble(Ui.textField2.getText());
        c = Double.parseDouble(Ui.textField3.getText());
        A = Double.parseDouble(Ui.textField4.getText());
        w = Double.parseDouble(Ui.textField5.getText());
        n = Double.parseDouble(Ui.textField6.getText());
        lineC = V * a * c;
        expC = (-1) * A * n;
    }

    /**
     * Processes parameters and creates graph data.
     * Any side-effects: Modifies the instance variables time, power, powerJ, powerU, tLimit, L, integral, integralJ, and integralU.
     */
    public void createGraph() {
        getParameters();

        int MAX_SIZE = 300000; // Maximum size for the arrays

        // Initialize the arrays with a fixed size
        power = new double[MAX_SIZE];
        powerJ = new double[MAX_SIZE];
        powerU = new double[MAX_SIZE];
        time = new double[MAX_SIZE]; // Changed to double type

        // Loop to calculate values and fill the arrays
        double currentTime = 0.0;
        int i = 0;
        while (i < MAX_SIZE) {
            time[i] = currentTime;
            power[i] = lineC * Math.exp(-A * time[i]) * (1 - Math.exp(expC * time[i]));
            if(testing)System.out.println(power[i]);
            powerJ[i] = w * c * (1 - Math.exp(expC * time[i]));
            if(testing)System.out.println(powerJ[i]);
            powerU[i] = power[i] - powerJ[i];
            if (power[i] != 0 && powerJ[i] != 0 && power[i] <= powerJ[i]) break;
            currentTime += 0.0077152; // Increment time
            // Ensures error under 0,1% for initial values
            i++;
        }
        tLimit = time[i]; // Set tLimit to the value of the last element in time[]
        if(testing)System.out.println(tLimit);
        L = i;

        integral = riemannIntegration(power, tLimit);
        integralJ = riemannIntegration(powerJ, tLimit);
        integralU = riemannIntegration(powerU, tLimit);

        // for testing
        if(testing)System.out.println("Integral of power[]: " + integral);
        if(testing)System.out.println("Integral of powerJ[]: " + integralJ);
        if(testing)System.out.println("Integral of powerU[]: " + integralU);
    }

    /**
     * Performs Riemann sum method integration for a given function.
     *
     * @param function The function array to integrate. Should never be null.
     * @param tLimit   The right-side limit. Should never be null.
     * @return The integral value.
     * @throws IllegalArgumentException if the length of the function array is less than 1.
     */
    public static double riemannIntegration(double[] function, double tLimit) {
        if (function.length < 1) {
            throw new IllegalArgumentException("Length of function array must be at least 1");
        }
        
        int numIntervals = function.length - 1;
        double sum = 0;

        for (int i = 0; i < numIntervals; i++) {
            double intervalTime = tLimit / numIntervals;
            double functionValue = function[i];
            sum += functionValue * intervalTime;
        }

        return sum;
    }
}
