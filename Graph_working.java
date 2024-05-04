package ui;

import java.util.Random;

/**
 * This class represents a graph and contains methods for creating and processing graph data.
 */
public class Graph {
	// testing mode console comments
	boolean console = true;

    // parameters
    double V, a, c, A, w, n;
    // simplification constants
    double lineC, expC;

    // tables
    double tLimit;

    double power[];
    double powerJ[];
    double powerU[];

    // Integral values
    double integral;
    double integralJ;
    double integralU;

    //int used for getting the number of data needed for graph
    static int L; 
    /**
     * Imports parameters from text fields in the UI class.
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
     */
    public void createGraph() {
        getParameters();

        int MAX_SIZE = 300000; // Maximum size for the arrays

        // Initialize the arrays with a fixed size
        power = new double[MAX_SIZE];
        powerJ = new double[MAX_SIZE];
        powerU = new double[MAX_SIZE];

        // Loop to calculate values and fill the arrays
        double currentTime = 0.0;
        int i = 0;
        while (i < MAX_SIZE) {
            currentTime = i /200;	// Arbitrary time interval that works
        	power[i] = lineC * Math.exp(-A * currentTime) * (1 - Math.exp(expC * currentTime));
        	if(console)	System.out.println(power[i]);
            
            powerJ[i] = w * c * (1 - Math.exp(expC * currentTime));
            if(console)	System.out.println(powerJ[i]);
            
            powerU[i] = power[i] - powerJ[i];
            if(console) System.out.println(powerU[i]);
            
            if (power[i] < powerJ[i])	{
            	if(i < MAX_SIZE)	{
            		if(console)	System.out.println("stopped, index and current time:");
            		if(console)	System.out.println(i);
                	if(console)	System.out.println(currentTime);
            	}
            	break;
            }
            i++;
            
        }
        tLimit = currentTime;
        L = i;
        
        
        double maxPower = findMax(power);
        integral = monteCarloIntegration(power, tLimit, maxPower);

        double maxPowerJ = findMax(powerJ);
        integralJ = monteCarloIntegration(powerJ, tLimit, maxPowerJ);

        double maxPowerU = findMax(powerU);
        integralU = monteCarloIntegration(powerU, tLimit, maxPowerU);

        // for testing
        if(console)	System.out.println("Integral of power[]: " + integral);
        if(console)	System.out.println("Integral of powerJ[]: " + integralJ);
        if(console)	System.out.println("Integral of powerU[]: " + integralU);
    }


    /**
     * Finds the maximum value in an array.
     *
     * @param array The array to search for the maximum value.
     * @return The maximum value in the array.
     */
    public static double findMax(double[] array) {
        double max = array[0];
        for (double value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * Performs Monte Carlo method integration for a given function.
     *
     * @param function The function array to integrate.
     * @param tLimit   The right-side limit.
     * @param max      The maximum value in the function array.
     * @return The integral value.
     */
    public static double monteCarloIntegration(double[] function, double tLimit, double max) {
        int numSamples = 100000;

        Random random = new Random();
        double sum = 0;

        for (int i = 0; i < numSamples; i++) {
            double randomPoint = random.nextDouble() * tLimit;
            double functionValue = function[(int) randomPoint];
            sum += functionValue;
        }

        double average = sum / numSamples;
        double integral = average * tLimit;

        return integral;
    }
}
