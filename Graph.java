package data;


// Author: Jakub Fabiński
public class Graph {
	// parameters imported
	double V, a, c, A, w, n;
	// simplification constants
	double lineC, expC;
	
	// time tables
	int time[];
	int tLimit;
	int tIntrvl;
	
	protected void getParameters()	{
		// TO DO
		lineC = V * a * c;
		expC = (-1) * A * n;
	}
	
	public double[] Graph()	{
		
		// power table
		double power[] = null;
		
		for(int i = 0; i < tLimit; i+=tIntrvl)	{
			power[i] = lineC * Math.exp(-A*time[i]) * (1 - Math.exp(expC * time[i]));
		}
		
		return power;
	}
}
