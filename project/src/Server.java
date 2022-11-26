
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Server class
class Server {

	static HashMap<String, Integer> scores = new HashMap<>();
	static ArrayList<ClientHandler> clientThreads = new ArrayList<>();
	static ClientHandler adminPort;

	public static void main(String[] args) throws IOException {
		
		Socket admin;
		ServerSocket server = new ServerSocket(1234);
		Socket client;
		int counter = 1;
		
		while (true) {
			admin = server.accept();
			System.out.println("Admin Connected");
			
			DataOutputStream output = new DataOutputStream(admin.getOutputStream());
			DataInputStream input = new DataInputStream(admin.getInputStream());

			ClientHandler adminSock = new ClientHandler(admin, "admin", output, input);
			Thread t = new Thread(adminSock);
			adminPort = adminSock;
			t.start();
			break;
			
				
		}
		while (true) {

			client = server.accept();
			System.out.println("New client: " + client.getInetAddress());

			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			DataInputStream input = new DataInputStream(client.getInputStream());

			ClientHandler clientSock = new ClientHandler(client, "user" + counter, output, input);
			Thread t = new Thread(clientSock);
			clientThreads.add(clientSock);
			t.start();
			counter++;
		}

	}

}
