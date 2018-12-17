package pso;

public class Swarm {
	private Particle[] particles = new Particle[PSOConstants.PARTICLES];
	
	public Swarm() {
		
	}
	
	public void initialize() {
		for(int i = 0; i < PSOConstants.PARTICLES ; i++) {
			Particle newParticle = new Particle();
			newParticle.generateParticle();
			saveParticle(i, newParticle);
		}
	}
	
	public Particle getParticle(int index) {
		return this.particles[index];
	}
	
	//maksimum lub minimum
	public Particle getFittestParticle() {
		
		Particle fittest = particles[0];
		
		for(int i =0; i < PSOConstants.PARTICLES; i++) {
			fittest.getFitness();
			getParticle(i).getFitness();
			if(getParticle(i).fitness < fittest.fitness)
				fittest = getParticle(i);
		}
		
		return fittest;
	}
	
	public void saveParticle(int index, Particle particle) {
		this.particles[index] = particle;
	}
	
	public int size() {
		return this.particles.length;
	}
}
