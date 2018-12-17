package pso;

public class PSOapp {
	public static int option = 0;
	
	public static void main(String[] args) {
		try {
			Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.exit(0);
		}
		option = returnOption(Integer.parseInt(args[0]));
		new PSOAlgorithms().execute();
	}
	
	public static int returnOption(int i) {
		option =i;
		return option;
	}
}
