package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ga.*;
import pso.PSOapp;

public class Client {
	
		
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
        if((args.length == 0) || (args.length > 1)) {
        	System.out.println("Usage: java Client <username>");
        	System.exit(0);
        }
    	String name = args[0];
        Socket socket = new Socket("localhost", 4444);
        Reader r = new Reader(socket);
    	r.start();
        BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(name);
        BufferedReader bufferedReaderFromCmdPrompt = new java.io.BufferedReader(new InputStreamReader(System.in));
        while(true){
        	Thread.sleep(200);
        	printMenu();
            String readerInput = bufferedReaderFromCmdPrompt.readLine();
            String forCheck = readerInput;
            printWriter.println(readerInput);
            try {
            	Integer.parseInt(forCheck);
            } catch (NumberFormatException e) {
            	System.out.println("\n\n Error. Podaj liczbe. \n\n");
            	System.exit(0);
            }
            int choose = Integer.parseInt(forCheck);
            if (choose == 1) {
            	args[0] = "1";
        		GAapp.main(args);
            } else if (choose == 2) {
            	args[0] = "2";
        		GAapp.main(args);
            } else if (choose == 3) {
            	args[0] = "3";
            	GAapp.main(args);
            } else if(choose == 4) {
            	args[0] = "4";
            	GAapp.main(args);
            } else if (choose == 5) {
            	args[0] = "1";
            	PSOapp.main(args);   	
            } else if (choose == 6) {
            	args[0] = "2";
            	PSOapp.main(args); 
            } else if (choose == 7) {
            	args[0] = "3";
            	PSOapp.main(args); 
            } else if (choose == 8) {
            	args[0] = "4";
            	PSOapp.main(args); 
            } else if (choose == 9) {
            	System.out.println("Wyjscie z serwera");
            	System.exit(0);
            } else {
            	System.out.println("\nPodano zly numer.\n");
            }
        }
    }
    
    public static class Reader extends Thread {
    	Socket socket = new Socket();
    	Reader(Socket socket){
    		this.socket = socket;
    	}
    	public void run() {
    		while(true) {
    		try {
    			BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			if(bufferedReaderFromClient != null) {   				
    				String readed = bufferedReaderFromClient.readLine();
                	String reg =";";
                	String[] res = readed.split(reg);
                	for (String out : res) {
                        if (!"".equals(out)) {
                            System.out.print(out + "\n");
                        }
                	}
    			}
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    }
	
	private static void printMenu() {
		System.out.println("GA - Genetic Algorythm\nPSO - Particle Swarm Optimization\nWybierz opje:\n1. Funkcja Beale'a GA\n2. Funkcja Rosenbrocka GA\n3. Funkcja Booth'a GA\n4. Funkcja Easom'a GA\n5. Funkcja Beale'a PSO\n6. Funkcja Rosenbrocka PSO\n7. Funkcja Booth'a PSO\n8. Funkcja Easom'a PSO\n9. Wyjscie");
	}
	
}