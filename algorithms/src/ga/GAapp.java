package ga;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GAapp {
	public static int option = 0;
	public static void main(String[] args) throws IOException {
		try {
			Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.exit(0);
		}
		option = returnOption(Integer.parseInt(args[0]));
		XYDataset dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("X", true);
		XYSeries series2 = new XYSeries("Y", true);
		GeneticAlgorithms geneticAlgorithms = new GeneticAlgorithms();
		
		Population population = new Population(1000);
		population.initialize();
		
		int generationCounter = 0;
		
		while( generationCounter != GAConstants.SIMULATION_LENGTH) {
			
			++generationCounter;
			System.out.println("Generation: " + generationCounter + " - extremum is: " + population.getFittestIndividual().getFitness() + " for:");
			System.out.println("x = " + population.getFittestIndividual().getFitnessResult(0));
			System.out.println("y = " + population.getFittestIndividual().getFitnessResult(1) + "\n");
			double var1 = population.getFittestIndividual().getFitnessResult(0);
			double var2 = population.getFittestIndividual().getFitnessResult(1);
			double var3 = population.getFittestIndividual().getFitness();
			series2.add(var2, var3);
			series.add(var1, var3);
			population = geneticAlgorithms.evolvePopulation(population);
		}
		
		((XYSeriesCollection) dataset).addSeries(series);
		((XYSeriesCollection) dataset).addSeries(series2);
		LineChartEx chart = new LineChartEx(dataset);
		System.out.println("Solution found");
		System.out.println("x = " + population.getFittestIndividual().getFitnessResult(0));
		System.out.println("y = " + population.getFittestIndividual().getFitnessResult(1));
	    
	    SwingUtilities.invokeLater(() -> {
            chart.setVisible(true);
        });
	    
	}
	
	public static int returnOption(int i) {
		option =i;
		return option;
	}
	
}
