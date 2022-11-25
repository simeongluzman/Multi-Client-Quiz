
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Server class
class Server {

	static HashMap<String, Integer> scores = new HashMap<>();
	static ArrayList<ClientHandler> clientThreads = new ArrayList<>();
	
	

	public static void main(String[] args) {

		ServerSocket server = null;
		

		try {

			// server is listening on port 1234
			server = new ServerSocket(1234);
			

			// running infinite loop for getting
			// client request
			while (true) {

				// socket object to receive incoming client
				// requests

				Socket client = server.accept();

				// Displaying that new client is connected
				// to server
				System.out.println("New client connected" + client.getInetAddress().getHostAddress());

				// create a new thread object
				ClientHandler clientSock = new ClientHandler(client);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
				
				clientThreads.add(clientSock);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {
		
		private final Socket clientSocket;
		PrintWriter output;
		BufferedReader input;

		// Constructor
		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			
			try {

				// get the outputstream of client
				output = new PrintWriter(clientSocket.getOutputStream(), true);

				// get the inputstream of client
				input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				

				scores.put(input.readLine(), 0);
				
				//
				System.out.println(scores);
				
				BufferedReader kB
				= new BufferedReader(new InputStreamReader(System.in));
				String line = null;
				
				
				while (true) {

					// writing the received message from
					// client
					line = kB.readLine();
					output.println(line);
					System.out.printf(" Sent from the client: %s\n", input.readLine());
					//output.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (output != null) {
						output.close();
					}
					if (input != null) {
						input.close();
						clientSocket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void writeToAll(String out) {
			
			for (ClientHandler t: clientThreads) {
				
				output.println(out);
				
				
				
				
			}
			
			
		}
	}
}
