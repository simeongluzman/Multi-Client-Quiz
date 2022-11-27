
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

	String userName;
	Socket clientSocket;
	DataOutputStream output;
	DataInputStream input;
	// Scanner keyBoard = new Scanner(System.in);
	

	// Constructor
	public ClientHandler(Socket socket, String userName, DataOutputStream output, DataInputStream input) {

		this.clientSocket = socket;
		this.userName = userName;
		this.output = output;
		this.input = input;
		

	}

	public void run() {

		String inputLine;

		while (true) {
			try {
				inputLine = input.readUTF();
				System.out.println(inputLine);

				// add exit condition

				if (inputLine == "-exit") {
					this.clientSocket.close();
					break;
				}

				// add logic for admin ---> All
				// and All only to Admin
				
				if (inputLine.charAt(0) == '+') {
					
					for (ClientHandler client : Server.clientThreads) 

						client.output.writeUTF(inputLine.substring(1));
					
				}
				
				else {
					Server.adminPort.output.writeUTF(inputLine.substring(1));
				}
				

				
						
					

				}
			

			catch (IOException e) {
				e.printStackTrace();

			}

		}

		try {
			this.input.close();
			this.output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// scores.put(input.readLine(), 0);
	// System.out.println(scores);
	/*
	 * private void writeToAll(String out) {
	 * 
	 * for (ClientHandler t : clientThreads) {
	 * 
	 * output.println(out);
	 * 
	 * }
	 * 
	 * }
	 */

}
