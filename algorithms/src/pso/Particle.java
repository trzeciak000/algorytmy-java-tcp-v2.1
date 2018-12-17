package pso;

import java.util.Random;

public class Particle {
	private Vectors position = new Vectors(0,0);
	private Vectors velocity = new Vectors(0,0);
	public double fitness;
	private Random randomGenerator;
	
	public void generateParticle() {
		this.position.setX(randomGenerator.nextDouble() * (5 - (-5)) -5);
		this.position.setY(randomGenerator.nextDouble() * (5 - (-5)) -5);
		
		this.velocity.setX(randomGenerator.nextDouble() * (5 - (-5)) -5);
		this.velocity.setY(randomGenerator.nextDouble() * (5 - (-5)) -5);
	}
	
	public Particle() {
		this.randomGenerator = new Random();
	}
	
	public void setVelocity(Vectors velocity) {
		this.velocity = velocity;
	}
	
	public Vectors getVelocity() {
		return velocity;
	}
	
	public void setPosition(Vectors position) {
		this.position = position;
	}
	
	public Vectors getPosition() {
		return position;
	}
	
	public void getFitness() {
		int option = PSOapp.option;
		double x = this.position.getX();
		double y = this.position.getY();
		if (option == 1) {
			//funkcja Beale'a
		fitness = Math.pow((1.5-x+(x*y)), 2) + Math.pow((2.25-x) + (x*Math.pow(y, 2)), 2) + 
				Math.pow(((2.625 - x) + (x*Math.pow(y, 3))), 2);
		}
		else if (option == 2) {
			//funkcja Rosenbrocka
			fitness = (Math.pow((1-x),2)) + (100*(Math.pow((y-Math.pow(x,2)),2)));
		} else if(option == 3) {
			//funkcja Booth'a
			fitness = Math.pow((x + (2*y) -7), 2) + Math.pow(((2*x) + y - 5), 2);
		} else if (option == 4) {
			//funkcja Easom'a
			fitness = -Math.cos(x)*Math.cos(y)*Math.exp(-(Math.pow((x - Math.PI), 2) + Math.pow(y - Math.PI, 2)) );
		}
	}
}
