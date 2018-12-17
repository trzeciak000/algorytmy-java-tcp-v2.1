package pso;

import java.util.Random;
import java.util.Vector;

import javax.swing.SwingUtilities;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ga.LineChartEx;
public class PSOAlgorithms {
	public Swarm swarm = new Swarm();
	private double[] pBest = new double[PSOConstants.PARTICLES];
	private Vector<Vectors> pBestLocation = new Vector<Vectors>();
	private double gBest;
	private Vectors gBestLocation;
	private double[] fitnessValueList = new double[PSOConstants.PARTICLES];
	
	Random generator = new Random();
	
	XYDataset dataset = new XYSeriesCollection();
	XYSeries series = new XYSeries("X", true);
	XYSeries series2 = new XYSeries("Y", true);
	
	public void execute() {
		swarm.initialize();
		updateFitnessList();
		for(int i=0; i<PSOConstants.PARTICLES; i++) {
			pBest[i] = fitnessValueList[i];
			pBestLocation.add(swarm.getParticle(i).getPosition());
		}
		
		int t = 0;
		double w;
		double err = 9999;
		
		while(t < PSOConstants.SIMULATION_LENGTH) {
			// krok 1 - aktualizacja najlepszej wartoœci i pozycji lokalnej.
			for(int i=0; i<PSOConstants.PARTICLES; i++) {
				if(fitnessValueList[i] < pBest[i]) {
					pBest[i] = fitnessValueList[i];
					pBestLocation.set(i, swarm.getParticle(i).getPosition());
				}
			}
				
			// krok 2 - aktualizacja najlepszej wartoœci i pozycji globalnej.
			int bestParticleIndex = getMinPos(fitnessValueList);
			if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
				gBest = fitnessValueList[bestParticleIndex];
				gBestLocation = swarm.getParticle(bestParticleIndex).getPosition();
			}
			
			w = PSOConstants.W_UPPERBOUND - (((double) t) / PSOConstants.SIMULATION_LENGTH) * (PSOConstants.W_UPPERBOUND - PSOConstants.W_LOWERBOUND);
			
			for(int i=0; i<PSOConstants.PARTICLES; i++) {
				// krok 3 - aktualizacja pêdkoœci
				 double r1 = generator.nextDouble();
				 double r2 = generator.nextDouble();
				 double px = swarm.getParticle(i).getPosition().getX();
				 double py = swarm.getParticle(i).getPosition().getY();
				 double vx = swarm.getParticle(i).getVelocity().getX();
				 double vy = swarm.getParticle(i).getVelocity().getY();
				 double pBestX = pBestLocation.get(i).getX();
				 double pBestY = pBestLocation.get(i).getY();
				 double gBestX = gBestLocation.getX();
				 double gBestY = gBestLocation.getY();
				 
				 double newVelX = (w * vx) + (r1 * PSOConstants.C1) * (pBestX - px) + (r2 * PSOConstants.C2) * (gBestX - px);
				 double newVelY = (w * vy) + (r1 * PSOConstants.C1) * (pBestY - py) + (r2 * PSOConstants.C2) * (gBestY - py);
				 swarm.getParticle(i).setVelocity(new Vectors(newVelX, newVelY));
				 
				 // aktualizacja pozycji cz¹stki
				 double newPosX = px + newVelX;
				 double newPosY = py + newVelY;
				 swarm.getParticle(i).setPosition(new Vectors(newPosX, newPosY));
			}
			
			int option = PSOapp.option;
			double x = gBestLocation.getX();
			double y = gBestLocation.getY();
			if (option == 1) {
				//funkcja Beale'a
				err = Math.pow((1.5-x+(x*y)), 2) + Math.pow((2.25-x) + (x*Math.pow(y, 2)), 2) + 
					Math.pow(((2.625 - x) + (x*Math.pow(y, 3))), 2);
			}
			else if (option == 2) {
				//funkcja Rosenbrocka
				err = (Math.pow((1-x),2)) + (100*(Math.pow((y-Math.pow(x,2)),2)));
			} else if(option == 3) {
				//funkcja Booth'a
				err = Math.pow((x + (2*y) -7), 2) + Math.pow(((2*x) + y - 5), 2);
			} else if (option == 4) {
				//funkcja Easom'a
				err = -Math.cos(x)*Math.cos(y)*Math.exp(-(Math.pow((x - Math.PI), 2) + Math.pow(y - Math.PI, 2)) );
			}
			
			
			System.out.println("Iteriation: " + t + " - extremum is: " + err);
			System.out.println("x = " + gBestLocation.getX());
			System.out.println("y = " + gBestLocation.getY());
			double var1 = gBestLocation.getX();
			double var2 = gBestLocation.getY();
			double var3 = err;
			series2.add(var2, var3);
			series.add(var1, var3);
			
			t++;
			updateFitnessList();
		}
		((XYSeriesCollection) dataset).addSeries(series);
		((XYSeriesCollection) dataset).addSeries(series2);
		LineChartEx chart = new LineChartEx(dataset);
		
		System.out.println("\nSolution found");
		System.out.println("x = " + gBestLocation.getX());
		System.out.println("y = " + gBestLocation.getY());
		
		SwingUtilities.invokeLater(() -> {
            chart.setVisible(true);
        });
	}
	
	public void updateFitnessList() {
		for(int i=0; i < PSOConstants.PARTICLES; i++) {
			swarm.getParticle(i).getFitness();
			fitnessValueList[i] = swarm.getParticle(i).fitness;
		}
	}
	
	public static int getMinPos(double[] list) {
		int pos = 0;
		double minValue = list[0];
		
		for(int i=0; i<list.length; i++) {
			if(list[i] < minValue) {
				pos = i;
				minValue = list[i];
			}
		}
		
		return pos;
	}
}
