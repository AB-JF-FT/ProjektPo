package data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

import ui.Ui;

/**
 * Class for creating and integrating a data set
 * <p>
 * Authors:
 * Main author: Jakub Fabiński
 * Sub author: Andrzej Brzostowicz
 */
public class Graph {
	// parameters
	double V, a, c, A, w, n;
	// simplification constants
	double lineC, expC;
	
	// tables
	int time[];
	int tLimit;
//	int tIntrvl;
	
	double power[];
	double powerJ[];
	double powerU[];
	
	// Integral values
	double integral;
	double integralJ;
	double integralU;
	
	
	/**
     * Imports parameters from text fields
     */
	protected void getParameters()	{
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
     * Processes parameters
     */
	public void createGraph()	{
		
	//	double power[] = null;
	//	double powerJ[] = null;
	//	double powerU[] = null;
		
		int i = 0;
		while(powerU[i] >= 0)	{
			time[i] = i;
			power[i] = lineC * Math.exp(-A*time[i]) * (1 - Math.exp(expC * time[i]));
			powerJ[i] = w * c * (1 - Math.exp(expC * time[i]));
			powerU[i] = power[i] - powerJ[i];
			i++;
		}
		tLimit = i;
        
		double maxPower = findMax(power);
		integral = monteCarloIntegration(power, tLimit, maxPower);
		
        double maxPowerJ = findMax(powerJ);
        integralJ = monteCarloIntegration(powerJ, tLimit, maxPowerJ);
        
        double maxPowerU = findMax(powerU);
        integralU = monteCarloIntegration(powerU, tLimit, maxPowerU);
        
        // for testing
        System.out.println("Całka z power[]: " + integral);
        System.out.println("Całka z powerJ[]: " + integralJ);
        System.out.println("Całka z powerU[]: " + integralU);
		
		/*
		for(int i = 0; i < tLimit; i+=tIntrvl)	{
			power[i] = lineC * Math.exp(-A*time[i]) * (1 - Math.exp(expC * time[i]));
		}
		*/
       
		
	}
	// Constructor
	public void Graph() {
		getParameters();
		createGraph();
	}
	
	// Returning data
	
	public double[] getPower() {
		return power;
    }
	public double[] getPowerJ() {
		return powerJ;
    }
	public double[] getPowerU() {
		return powerU;
    }
	
	public double getIntegral() {
		return integral;
    }
	public double getIntegralJ() {
		return integralJ;
    }
	public double getIntegralU() {
		return integralU;
    }
	
	
	/**
	 * @param any array
     * @return Maximum in array
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
	 * Monte Carlo method integration
	 * @param function array
	 * @param tLimit right-side limit
     * @return integral value
     */
	public static double monteCarloIntegration(double[] function, int tLimit, double max) {
		int numSamples = 100000;
		
		Random random = new Random();
		double sum = 0;
		
		for (int i = 0; i < numSamples; i++) {
			double randomPoint = random.nextDouble() * tLimit;
			double functionValue = function[(int)randomPoint];
			sum += functionValue;
		}
		
		double average = sum / numSamples;
		double integral = average * tLimit;
		
		return integral;
	}
}