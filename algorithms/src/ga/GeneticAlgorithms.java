package ga;

import java.util.Random;

public class GeneticAlgorithms {
	
	private Random randomGenerator;
	
	public GeneticAlgorithms() {
		this.randomGenerator = new Random();
	}
	
	public Population evolvePopulation(Population population) {
		
		Population newPopulation = new Population(population.size());
		
		for(int i = 0; i < population.size(); i++) {
			Individual firstIndividual = randomSelection(population);
			Individual secondIndividual = randomSelection(population);
			Individual newIndividual = crossover(firstIndividual, secondIndividual);
			newPopulation.saveIndividual(i, newIndividual);
		}
		
		for(int i = 0; i < newPopulation.size(); i++)
			mutate(newPopulation.getIndividual(i));
		
		return newPopulation;
	}
	
	private void mutate(Individual individual) {
		
		for (int i = 0; i < (GAConstants.CHROMOSOME_LENGTH*2); i++) {
			if(Math.random() <= GAConstants.MUTATION_RATE) {
				int gene = randomGenerator.nextInt(2);
				individual.setGene(i, gene);
			}
		}
	}
	
	private Individual crossover(Individual firstIndividual, Individual secondIndividual) {
		
		Individual newSolution = new Individual();
		
		for ( int i = 0; i < (GAConstants.CHROMOSOME_LENGTH*2); i++) {
			if(Math.random() <= GAConstants.CROSSOVER_RATE) {
				newSolution.setGene(i, firstIndividual.getGene(i));
			} else {
				newSolution.setGene(i, secondIndividual.getGene(i));
			}
		}
		return newSolution;
	}
	
	private Individual randomSelection(Population population) {
		
		Population newPopulation = new Population(GAConstants.TOURNAMENT_SIZE);
		
		for(int i = 0; i < GAConstants.TOURNAMENT_SIZE; i++) {
			int randomIndex = (int) (Math.random() * population.size());
			newPopulation.saveIndividual(i, population.getIndividual(randomIndex));
		}
		
		Individual fittestIndividual = newPopulation.getFittestIndividual();
		
		return fittestIndividual;
	}
	
}
