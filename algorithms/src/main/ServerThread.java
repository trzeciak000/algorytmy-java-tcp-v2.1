package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket socket;
    PrintWriter outputWriter;
    ServerThread(Socket socket){
        this.socket = socket;
        try {
        	outputWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
    }
    public void run(){
        try {
            String message = null;
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String name = bufferedReader.readLine();
            System.out.println("user '" + name + "' is now connected to the server");
            while (true) {
            	message = bufferedReader.readLine();
            	try {
            		Integer.parseInt(message);
            	} catch (NumberFormatException e) {
            		System.exit(0);
            	}
            	int opt = Integer.parseInt(message);
            	if (opt == 1) message = "1. funkcja Beale'a GA";
            	if (opt == 2) message = "2. funkcja Rosenbrocka GA";
            	if (opt == 3) message = "3. funkcja Booth'a GA";
            	if (opt == 4) message = "4. funkcja Easom'a GA";
            	if (opt == 5) message = "5. funkcja Beale'a PSO";
            	if (opt == 6) message = "6. funkcja Rosenbrocka PSO";
            	if (opt == 7) message = "7. funkcja Booth'a PSO";
            	if (opt == 8) message = "8. funkcja Easom'a PSO";
            	if (opt == 9) {
            		message = "5. Wyjscie";
            		socket.close();
            	}
                System.out.println("Klient '" + name + "' wybral: " + message);
            }
        } catch (IOException e) {
        	
        }
    }
}
